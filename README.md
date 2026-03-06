# FlowSync_Microservice

<!-- Registration Flow -->

Client
   │
   ▼
API Gateway
   │
   ▼
Auth Service
   │
   ├── Generate UUID
   ├── Save credentials
   │
   ▼
Call User Service
   │
   ▼
User Service
Create User Profile using SAME UUID





<!-- Login Flow  -->

Client
   │
   ▼
API Gateway
   │
   ▼
Auth Service
   │
   ├── Validate password
   ├── Generate JWT
   │
   ▼
Return Token


<!-- Authenticated Request Flow -->

Client (JWT Token)
   │
   ▼
API Gateway
   │
   ├── Validate JWT
   ├── Extract userId
   │
   ▼
User Service
   │
   ├── Fetch user profile using UUID
   │
   ▼
Response

