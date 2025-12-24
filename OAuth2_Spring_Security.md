# OAuth2 Authentication & Spring Security – CareerSense

**Version:** v1.0 (Production Ready)

---

## Purpose
This document summarizes how **Google OAuth2**, **Spring Security**, and **JWT** are used in CareerSense to implement a secure, scalable, stateless authentication system.

---

## Why OAuth2
Traditional username/password authentication introduces storage risks, reset complexity, and security vulnerabilities.  
CareerSense uses **Google OAuth2** to:

- Avoid password storage
- Leverage Google’s enterprise-grade security
- Improve user trust and onboarding
- Support MFA and fraud protection by default

---

## OAuth2 Roles

| Role | Description |
|---|---|
| Resource Owner | End user |
| Client | CareerSense |
| Authorization Server | Google |
| Resource Server | CareerSense Backend |

---

## OAuth2 Flow (Authorization Code)

1. User opens `index.html`
2. Redirect to `/oauth2/authorization/google`
3. Google login & consent
4. Callback to `/login/oauth2/code/google`
5. `OAuth2SuccessHandler` issues JWT and redirects to `/home.html`

---

## OAuth2SuccessHandler (Core Logic)

Acts as the bridge between Google authentication and application security.

**Responsibilities:**
- Extract user details (email, name, picture)
- Create or update user in MongoDB
- Generate signed JWT
- Store JWT in **HttpOnly cookie**
- Redirect user to dashboard

---

## JWT Authentication Strategy

CareerSense uses **JWT** for stateless session management, enabling horizontal scalability.

### JWT Components

| Part | Purpose |
|---|---|
| Header | Signing algorithm |
| Payload | User identity & expiry |
| Signature | Tamper protection |

### Storage
JWT is stored in an **HttpOnly cookie**:
- Protects against XSS
- Works with `SameSite` for CSRF mitigation
- JavaScript cannot access the token

---

## JwtFilter – Runtime Authorization

Since the backend is stateless, authentication is revalidated on every request.

**Flow:**
1. Read JWT from cookie
2. Verify signature & expiration
3. Check user existence in MongoDB
4. Populate `SecurityContextHolder`
5. Auto-logout if user is deleted

---

## SecurityConfig – Access Control

Defines route-level authorization.

| Route | Access |
|---|---|
| `/index.html`, static assets | Public |
| `/oauth2/**` | Public |
| `/home.html`, `/api/**` | Authenticated |

Static pages are protected **server-side**, preventing unauthorized rendering.

---

## Logout & Session Invalidation

- `/api/logout` clears JWT cookie (`Max-Age=0`)
- Browser deletes the token immediately
- Without JWT, all protected routes are blocked

---

## Security Guarantees

- No password handling
- Stateless and scalable architecture
- Secure cookie-based authentication
- Server-side authorization
- Instant access revocation via database checks

---

**Conclusion:**  
CareerSense implements OAuth2 + Spring Security using industry best practices, resulting in a secure, scalable, and production-ready authentication system.