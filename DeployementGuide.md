#  CareerSense – Production Deployment Guide

Use this guide to deploy your Spring Boot application to AWS EC2 using Docker, Nginx, and GitHub Actions.

## 1️⃣ Project Architecture

**Traffic Flow:**
Client Browser ➔ HTTPS (DuckDNS) ➔ Nginx (Reverse Proxy Port 443) ➔ Docker (Spring Boot Port 8080) ➔ MongoDB Atlas

## 2️⃣ Prerequisites

Before you start, make sure you have:

- AWS Account with an EC2 Key Pair (.pem file)
- MongoDB Atlas Cluster (Free tier is fine)
- Google Cloud Console (For OAuth Client ID & Secret)
- Docker Hub Account (To store your images)
- GitHub Repository (With your Spring Boot code)
- DuckDNS Account (For a free domain name)

## 3️⃣ AWS EC2 Setup

**Step 1: Create EC2 Instance**
- OS: Ubuntu 22.04
- Type: t2.micro
- Security Group Rules: Open Ports 22 (SSH), 80 (HTTP), 443 (HTTPS), and 8080 (Custom TCP)

**Step 2: Connect to EC2**
Open your terminal and run:

```bash
# Give read-only permission to your key
chmod 400 CareerSense.pem

# Connect to the server
ssh -i CareerSense.pem ubuntu@YOUR_PUBLIC_IP
```

## 4️⃣ Install Docker
- sudo apt update
- sudo apt install docker.io -y

# Start and enable Docker
- sudo systemctl start docker
- sudo systemctl enable docker

# Allow 'ubuntu' user to run Docker without sudo
- sudo usermod -aG docker ubuntu

# Exit the server and log in again to apply changes
exit

## 5️⃣ Install & Configure Nginx
Nginx will take web traffic and send it to your Spring Boot app.

- sudo apt install nginx -y
- sudo systemctl start nginx
- sudo systemctl enable nginx

# Open the default Nginx config file
- sudo nano /etc/nginx/sites-available/default

Replace everything inside the file with this code:

````
server {
listen 80;
server_name careersense.duckdns.org;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
````

Save the file (Ctrl+O, Enter, Ctrl+X) and restart Nginx:

- sudo nginx -t
- sudo systemctl restart nginx


## 6️⃣ Domain & SSL (HTTPS) Setup
Go to DuckDNS.org and create a subdomain (e.g., careersense)

Point it to your EC2 Public IP

Install the SSL certificate on your EC2 server:

- sudo apt install certbot python3-certbot-nginx -y

### Generate the certificate
- sudo certbot --nginx -d careersense.duckdns.org

Note: Choose option 2 when asked to automatically redirect HTTP to HTTPS.

### 7️⃣ Application Configuration
Dockerfile
Create a file named Dockerfile in the root folder of your project:

````
# Build stage
FROM maven:3.9.12-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
````

.dockerignore: Create a .dockerignore file in the root folder to keep the image small:

````
target/
.git/
.gitignore
README.md
*.log
````


## 8️⃣ GitHub Secrets for CI/CD
Go to your GitHub Repository ➔ Settings ➔ Secrets and variables ➔ Actions. Add these secrets:


## 9️⃣ GitHub Actions CI/CD Pipeline
Create this file in your project: .github/workflows/deploy.yml

````````
name: CI-CD Pipeline

on:
  push:
    branches:
      - main

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
          cache: maven

      - name: Build with Maven
        run: mvn clean package -DskipTests

      - name: Login to Docker Hub
        run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin

      - name: Build and Push Docker Image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/careersense:latest .
          docker push ${{ secrets.DOCKER_USERNAME }}/careersense:latest

      - name: Deploy to EC2
        uses: appleboy/ssh-action@v1.0.3
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USER }}
          key: ${{ secrets.EC2_SSH_KEY }}
          script: |
            docker pull ${{ secrets.DOCKER_USERNAME }}/careersense:latest
            
            docker stop careersense || true
            docker rm careersense || true
            docker image prune -f
            
            docker run -d \
              --name careersense \
              -p 8080:8080 \
              --restart always \
              -e MONGO_URI="${{ secrets.MONGO_URI }}" \
              -e GOOGLE_CLIENT_ID="${{ secrets.GOOGLE_CLIENT_ID }}" \
              -e GOOGLE_CLIENT_SECRET="${{ secrets.GOOGLE_CLIENT_SECRET }}" \
              -e JWT_SECRET="${{ secrets.JWT_SECRET }}" \
              ${{ secrets.DOCKER_USERNAME }}/careersense:latest
````````

## 🔟 Useful Maintenance Commands

Keep these commands handy to check your server if something goes wrong. Run them on your EC2 instance.

Check App Status:

````````
docker ps
docker logs -f careersense
````````


## Restart Everything (Emergency Fix):

``````
docker restart careersense
sudo systemctl restart nginx
``````

## Check Nginx Errors (If you get 502 Bad Gateway):

````
sudo tail -f /var/log/nginx/error.log
````