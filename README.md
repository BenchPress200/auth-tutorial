# 🔐 auth-tutorial
웹 브라우저에서 인증/인가 방식 학습을 위한 튜토리얼 코드입니다.

<br>

### 사용 기술
![TypeScript](https://img.shields.io/badge/TypeScript-5.3.3-3178C6?logo=typescript&logoColor=white)
![React](https://img.shields.io/badge/React-18.2.0-61DAFB?logo=react&logoColor=black) <br>
![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-5.3.5-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Security-6.2.1-6DB33F?logo=springsecurity&logoColor=white) <br>
![JWT](https://img.shields.io/badge/JWT-0.12.3-000000?logo=jsonwebtokens&logoColor=white)



<br><br>

## 🚀 Sequence Diagram

### ```회원가입```
```mermaid
sequenceDiagram
  participant Browser
  participant API Server

  Browser->>API Server: 회원가입 요청
  API Server-->>API Server: 이름 중복확인, 회원등록

  API Server->>Browser: 회원가입 응답
```
<br>

### ```로그인```
```mermaid
sequenceDiagram
  participant Browser
  participant API Server

  Browser->>API Server: 로그인 요청
  API Server-->>API Server: 계정정보 인증

  API Server->>Browser: 로그인 응답 (HTTP 헤더에 JWT 토큰 발급)
```

<br>

### ```OAuth 2.0 로그인```
```mermaid
  sequenceDiagram
  participant Browser
  participant API Server
  participant Oauth2 API Server
```

<br>

### ```회원정보 조회```
```mermaid
sequenceDiagram
  participant Browser
  participant API Server

  Browser->>API Server: 회원정보 요청
  API Server-->>API Server: jwt 검증 & 회원정보 조회

  API Server->>Browser: 회원정보 응답
```


<br><br>

## 🌁 Browser Client

### Login Page
- 로그인 요청
- 회원가입 페이지 이동

### Join Page
- 회원가입 요청

### Main Page
- 로그아웃 요청
- 회원정보 요청



<br><br>

## 📄 Backend API


| 기능 | Method | URL | 
|---|---|---|
| 회원가입 | POST | /api/v1/users |
| 로그인 | POST | /api/v1/auth/login |
| 로그아웃 | POST | /api/v1/auth/logout |
| 본인 아이디 조회 | GET | /api/v1/auth/whoami |
| 회원정보 요청 | GET | /api/v1/users/{userId} |

<br>

**회원가입 - [POST] ```/api/v1/users```**
- 요청<br>
  ```json
  HTTP Body
  {
    "name" : string,
    "password" : string
  }
  ```
- 응답<br>
  ```json
  HTTP Body
  {
    "status": 201,
    "message": "Join completed.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 400,
    "message": "Invalid value",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 409,
    "message": "Duplicated name",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 500,
    "message": "Server error",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```

<br>

**로그인 - [POST] ```/api/v1/auth/login```**
- 요청
  ```json
  HTTP Body
  {
    "name" : string,
    "password" : string
  }
  ```
- 응답
  ```json
  HTTP Header
  "Authorization": "Bearer {JWT}"

  HTTP Body
  {
    "status": 200,
    "message": "Login completed successfully.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 401,
    "message": "Authentication failed.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 500,
    "message": "Server error",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```

<br>

**로그아웃 - [POST] ```/api/v1/auth/logout```**
- 요청<br>
  ```json
  HTTP Header
  "Authorization": "Bearer {JWT}"
  ```
- 응답
  ```json
  HTTP Body
  {
    "status": 200,
    "message": "Logout completed successfully",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 401,
    "message": "Authentication failed",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```
  ```json
  HTTP Body
  {
    "status": 500,
    "message": "Server error",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  }
  ```

<br>

**본인 아이디 조회 - [GET] ```/api/v1/auth/whoami```**
- 요청
  ```json
  HTTP Header
  "Authorization": "Bearer {JWT}"
  ```
- 응답
  ```json
  {
    "status": 200,
    "message": "OK",
    "data": {
      "id": number
    },
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```
  ```json
  {
    "status": 401,
    "message": "Authentication failed.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```
  ```json
  {
    "status": 500,
    "message": "Server error",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```

<br>

**회원정보 조회 - [GET] ```/api/v1/users/{userId}```**
- 요청
  ```json
  HTTP Header
  "Authorization": "Bearer {JWT}"
  ```
- 응답
  ```json
  {
    "status": 200,
    "message": "OK",
    "data": {
      "id": number,
      "name": string
    },
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```
  ```json
  {
    "status": 401,
    "message": "Authentication failed.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```
  ```json
  {
    "status": 404,
    "message": "User with [id] not found.",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```
  ```json
  {
    "status": 500,
    "message": "Server error",
    "data": null,
    "timestamp": "yyyy-mm-ddThh:mm:ssZ"
  } 
  ```