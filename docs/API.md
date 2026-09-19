# JobSphere API Documentation

Base URL:

```text
http://localhost:8080
```

## Authentication

### Register

**POST** `/api/auth/register`

Example request:

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CANDIDATE"
}
```

Supported registration roles:

```text
CANDIDATE
RECRUITER
```

Admin registration is not allowed.

---

### Login

**POST** `/api/auth/login`

Example request:

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

The response contains a JWT token.

For protected endpoints, send the token in the Authorization header:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

# Jobs

## Get All Jobs

**GET** `/api/jobs`

Optional filters:

```text
/api/jobs?title=Java
/api/jobs?location=Pune
/api/jobs?company=Google
/api/jobs?jobType=FULL_TIME
```

## Get Job By ID

**GET** `/api/jobs/{id}`

## Create Job

**POST** `/api/jobs`

Required role:

```text
RECRUITER
```

Example request:

```json
{
  "title": "Java Backend Developer",
  "company": "JobSphere Technologies",
  "description": "Develop REST APIs using Java and Spring Boot.",
  "location": "Nashik",
  "jobType": "FULL_TIME",
  "skills": "Java, Spring Boot, MySQL",
  "salary": "6-10 LPA"
}
```

## Update Job

**PUT** `/api/jobs/{id}`

Required role:

```text
RECRUITER
```

Only the recruiter who owns the job can update it.

## Delete Job

**DELETE** `/api/jobs/{id}`

Required role:

```text
RECRUITER
```

A job containing applications cannot be deleted.

---

# Applications

## Apply For Job

**POST** `/api/applications/apply`

Required role:

```text
CANDIDATE
```

Example request:

```json
{
  "jobId": 1
}
```

A candidate cannot apply to the same job twice.

## Get Candidate Applications

**GET** `/api/applications/candidate`

Required role:

```text
CANDIDATE
```

## Get Job Applicants

**GET** `/api/applications/job/{jobId}`

Required role:

```text
RECRUITER
```

Only the owner of the job can view its applicants.

## Update Application Status

**PUT** `/api/applications/{applicationId}/status`

Required role:

```text
RECRUITER
```

Example request:

```json
{
  "status": "SHORTLISTED"
}
```

Available statuses:

```text
APPLIED
REVIEWING
SHORTLISTED
REJECTED
HIRED
```

---

# Candidate Profile

## Get Candidate Profile

**GET** `/api/candidate/profile`

Required role:

```text
CANDIDATE
```

## Create/Update Candidate Profile

**POST** `/api/candidate/profile`

Example request:

```json
{
  "phone": "9876543210",
  "education": "B.E. Computer Science and Design",
  "skills": "Java, Spring Boot, MySQL",
  "experience": "Fresher - Full Stack Developer",
  "resumeUrl": "https://example.com/resume.pdf"
}
```

---

# Recruiter Profile

## Get Recruiter Profile

**GET** `/api/recruiter/profile`

Required role:

```text
RECRUITER
```

## Create/Update Recruiter Profile

**POST** `/api/recruiter/profile`

Example request:

```json
{
  "companyName": "JobSphere Technologies",
  "companyDescription": "Technology company developing recruitment solutions.",
  "website": "https://example.com",
  "location": "Nashik, Maharashtra",
  "industry": "Information Technology",
  "companySize": "11-50 employees"
}
```

---
# Users

## Get All Users

**GET** `/api/users`

Required role:

```text
ADMIN
```

# Admin

All admin endpoints require:

```text
ROLE_ADMIN
```

## Get All Users

**GET** `/api/admin/users`

## Get All Jobs

**GET** `/api/admin/jobs`

## Get All Applications

**GET** `/api/admin/applications`

## Delete User

**DELETE** `/api/admin/users/{id}`

## Delete Job

**DELETE** `/api/admin/jobs/{id}`

## Delete Application

**DELETE** `/api/admin/applications/{id}`

---

# HTTP Status Codes

```text
200 OK
201 Created
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
500 Internal Server Error
```

---

# Error Handling

JobSphere provides centralized exception handling for:

* Validation errors
* Invalid login credentials
* Missing resources
* Duplicate applications
* Unauthorized operations
* Invalid job/application operations

Errors are returned in a structured JSON response.

Example:

```json
{
  "timestamp": "2026-09-18T16:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Cannot delete job because applications exist",
  "path": "/api/jobs/1"
}
```
