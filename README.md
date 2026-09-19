# JobSphere — Full-Stack Recruitment Platform

JobSphere is a full-stack recruitment platform that connects candidates and recruiters through a secure web application.

The platform provides separate workflows for **Candidates, Recruiters, and Administrators**, with JWT-based authentication, role-based authorization, job management, applications, profiles, and administrative controls.

---

## Features

### Candidate

* Candidate registration and login
* Browse available jobs
* Search jobs by title, location, company, and job type
* View complete job details
* Apply for jobs
* Prevent duplicate applications
* Track submitted applications
* View application status
* Manage candidate profile
* Manage education, skills, experience, and resume URL

### Recruiter

* Recruiter registration and login
* Recruiter dashboard
* Create job postings
* Edit job postings
* View recruiter-owned jobs
* View applicants for posted jobs
* Update application status
* Manage company profile
* View company information

### Administrator

* Secure admin authentication
* View all users
* View all jobs
* View all applications
* Access centralized administrative management

---

## Security

JobSphere implements authentication and authorization using:

* Spring Security
* JWT authentication
* Role-based access control
* Protected REST APIs
* Password hashing
* Unauthorized request handling
* Forbidden-role handling
* Environment-based configuration for sensitive values

Supported roles:

```text
CANDIDATE
RECRUITER
ADMIN
```

---

## Technology Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* JWT

### Frontend

* HTML5
* CSS3
* JavaScript
* Fetch API
* Responsive UI

### Development Tools

* Visual Studio Code
* MySQL
* XAMPP
* MySQL Workbench
* Postman
* Git
* GitHub

---

## Project Architecture

```text
JobSphere
│
├── frontend/
│   ├── HTML pages
│   ├── CSS
│   └── JavaScript
│
├── src/main/java/JobSphere/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── security/
│   └── service/
│
├── src/main/resources/
│   └── application.properties
│
├── docs/
│   └── API.md
│
├── pom.xml
├── README.md
└── .env.example
```

### Backend Architecture

```text
Frontend
   ↓
REST Controllers
   ↓
Service Layer
   ↓
Repository Layer
   ↓
JPA / Hibernate
   ↓
MySQL
```

### Security Flow

```text
Client
   ↓
Login
   ↓
JWT Token
   ↓
Spring Security Filter
   ↓
Role Authorization
   ↓
Protected REST API
```

---

## Main Modules

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

Handles:

* User registration
* User authentication
* JWT generation
* Role assignment

### Jobs

```text
GET    /api/jobs
GET    /api/jobs/{id}
POST   /api/jobs
PUT    /api/jobs/{id}
DELETE /api/jobs/{id}
```

Provides:

* Job searching
* Job filtering
* Job details
* Job creation
* Job editing
* Job deletion

Recruiters can manage their own job postings.

### Applications

```text
POST /api/applications
GET  /api/applications/candidate
GET  /api/applications/recruiter
PUT  /api/applications/{id}/status
```

Provides:

* Job applications
* Duplicate application prevention
* Candidate application tracking
* Recruiter applicant management
* Application status updates

### Candidate Profile

Candidate profiles support:

* Phone number
* Education
* Skills
* Experience
* Resume URL

### Recruiter Profile

Recruiter profiles support:

* Company name
* Company description
* Website
* Location
* Industry
* Company size

### Administration

Administrators can access:

* All users
* All jobs
* All applications

Administrative APIs are protected using role-based authorization.

---

## Database

JobSphere uses **MySQL** with **Spring Data JPA / Hibernate**.

### Main Entities

```text
User
│
├── CandidateProfile
│
└── RecruiterProfile

Recruiter
   │
   └── Job
         │
         └── Application
               │
               └── Candidate
```

### Main Tables

```text
users
candidate_profiles
recruiter_profiles
jobs
applications
```

---

## Validation & Error Handling

The backend includes centralized exception handling and request validation.

| Situation                |      HTTP Status |
| ------------------------ | ---------------: |
| Successful request       |           200 OK |
| Resource created         |      201 Created |
| Invalid request data     |  400 Bad Request |
| Duplicate application    |  400 Bad Request |
| Invalid resource ID      |    404 Not Found |
| Missing authentication   | 401 Unauthorized |
| Invalid login            | 401 Unauthorized |
| Insufficient permissions |    403 Forbidden |

---

## Testing

The application was tested through REST API and frontend workflows.

### Functional Test Results

|  # | Test Case                              | Result |
| -: | -------------------------------------- | ------ |
|  1 | Candidate login                        | PASS   |
|  2 | Candidate profile                      | PASS   |
|  3 | Job search                             | PASS   |
|  4 | Job filtering                          | PASS   |
|  5 | Job details                            | PASS   |
|  6 | Duplicate application validation       | PASS   |
|  7 | Candidate applications                 | PASS   |
|  8 | Recruiter login                        | PASS   |
|  9 | Recruiter profile                      | PASS   |
| 10 | Recruiter create job                   | PASS   |
| 11 | Recruiter update job                   | PASS   |
| 12 | Recruiter view applicants              | PASS   |
| 13 | Recruiter update application status    | PASS   |
| 14 | Candidate sees updated status          | PASS   |
| 15 | Admin login                            | PASS   |
| 16 | Admin users                            | PASS   |
| 17 | Admin jobs                             | PASS   |
| 18 | Admin applications                     | PASS   |
| 19 | User management API                    | PASS   |
| 20 | Candidate blocked from creating jobs   | PASS   |
| 21 | Recruiter blocked from applying        | PASS   |
| 22 | Candidate blocked from admin APIs      | PASS   |
| 23 | Candidate blocked from user management | PASS   |
| 24 | Empty job validation                   | PASS   |
| 25 | Invalid login handling                 | PASS   |
| 26 | Invalid job ID handling                | PASS   |
| 27 | Invalid application ID handling        | PASS   |
| 28 | Job deletion restriction               | PASS   |
| 29 | Missing JWT token handling             | PASS   |
| 30 | Final application health check         | PASS   |

**Total Test Cases: 30**

**Result: 30/30 Passed**

---

## Local Setup

### Prerequisites

* Java JDK
* MySQL
* Git
* Visual Studio Code or another IDE

Maven does not need to be installed separately because the project includes the Maven Wrapper.

### 1. Clone the Repository

```bash
git clone https://github.com/Mohan-R-Bodke/jobsphere.git
cd jobsphere
```

### 2. Create the Database

```sql
CREATE DATABASE jobsphere;
```

### 3. Configure Environment Variables

Use `.env.example` as a reference.

Required properties:

```text
DB_PASSWORD=
JWT_SECRET=
JWT_EXPIRATION=3600000
```

For security, real credentials and secrets should remain in local configuration and must not be committed to GitHub.

### 4. Start MySQL

If using XAMPP, start:

```text
MySQL
```

Apache is **not required** to run the Spring Boot backend.

### 5. Run the Backend

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The Spring Boot application runs on:

```text
http://localhost:8080
```

### 6. Open the Frontend

The frontend is located in:

```text
frontend/
```

Open:

```text
frontend/index.html
```

The frontend communicates with:

```text
http://localhost:8080/api
```

---

## API Documentation

Detailed API documentation is available in:

```text
docs/API.md
```

It covers:

* Authentication
* Users
* Jobs
* Applications
* Candidate profiles
* Recruiter profiles
* Admin endpoints
* Request formats
* Response formats
* Authorization requirements

---

## Project Highlights

* Full-stack Java application
* Spring Boot REST API
* Spring Security integration
* JWT-based authentication
* Role-based authorization
* MySQL database
* JPA/Hibernate persistence
* Layered backend architecture
* DTO-based API design
* Centralized exception handling
* Request validation
* Candidate job search
* Recruiter job management
* Application tracking
* Application status management
* Administrative management
* Responsive frontend
* REST API testing
* 30 functional test cases completed

---

## Future Enhancements

* Resume file upload
* Email notifications
* Job recommendation system
* Advanced candidate-job matching
* Pagination for large datasets
* Recruiter analytics dashboard
* Candidate skill matching
* Cloud deployment
* Docker containerization
* CI/CD pipeline
* Automated unit and integration testing
* Production database configuration

---

## Author

**Mohan R. Bodake**

Computer Science and Design

GitHub: [https://github.com/Mohan-R-Bodke/jobsphere](https://github.com/Mohan-R-Bodke/jobsphere)

---

## License

This project is intended for educational, portfolio, and demonstration purposes.
