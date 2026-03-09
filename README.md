# CareerSense

Visit the site here: [CareerSense](https://careersense.duckdns.org/)

CareerSense is a full-stack SaaS application that helps users analyze their resumes and discover relevant job opportunities. The platform provides secure authentication, resume parsing, skill extraction, and job matching through a clean and modern user interface.

## Overview

CareerSense enables users to:

* **Secure Login:** Authenticate using Google OAuth.
* **Resume Parsing:** Upload resumes in PDF or DOCX format.
* **Skill Extraction:** Automatically extract professional skills from uploaded files.
* **Job Discovery:** Find relevant job opportunities based on extracted skills.
* **Protected Dashboard:** Navigate a secure interface with restricted access for authenticated users.

The application follows modern SaaS and security best practices and is designed to be scalable and extensible.

---

## Features

### 📊 Resume Score Checker
* Upload resume files.
* Parse resume content.
* Analyze resume quality and structure.

### 💼 Job Matching
* Upload resume and extract skills.
* Generate job recommendations based on skills.
* Redirect users to external job platforms using apply links.
* Accessible only to authenticated users.

### 🏠 Dashboard
* Displays user profile information.
* Provides navigation to Resume Score and Job Matching features.
* Clean and professional UI layout.

---

## Tech Stack

### Backend
* **Language:** Java 17
* **Framework:** Spring Boot 3
* **Security:** Spring Security, OAuth2 (Google Login), JWT Authentication
* **Database:** MongoDB
* **Library:** Apache Tika (Resume Parsing)

### Frontend
* HTML5
* CSS3
* JavaScript (ES6+)

### Tools and Libraries
* **Build Tool:** Maven
* **HTTP Client:** OkHttp
* **Web Scraping/Parsing:** Jsoup
* **Utility:** Lombok

---

## Application Flow

1.  User logs in using **Google OAuth**.
2.  **JWT** is generated and stored securely.
3.  User is redirected to the **Dashboard**.
4.  From the dashboard:
    * User can check **Resume Score**.
    * User can navigate to **Job Matching**.
5.  In Job Matching:
    * User uploads a resume.
    * Skills are extracted.
    * Matching job links are displayed.
6.  User can **logout** at any time.

---

## Security

* Stateless **JWT-based** authentication.
* **OAuth2** login integration with Google.
* Protected UI routes enforced by **Spring Security**.
* API endpoints secured with authentication checks.
* Unauthorized API requests return **HTTP 401**.
* Unauthorized UI access redirects to the login page.

---

## Project Structure

```text
CareerSense
│
├── src/main/java
│   └── com.careersense.app
│       ├── security
│       ├── jobmatch
│       ├── resume
│       └── user
│
├── src/main/resources
│   ├── static
│   │   ├── home.html
│   │   ├── job-match.html
│   │   ├── resume-score.html
│   │   ├── style.css
│   │   └── home.js
│   └── application.properties
│
├── pom.xml
└── README.md
