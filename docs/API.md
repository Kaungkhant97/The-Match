# The Match - API Specification

## Base URL

- **Development:** `http://localhost:8080/api`
- **Production:** `https://api.thematch.app/api` (configure in environment)

## Authentication

All endpoints except `/api/auth/*` require a valid JWT token in the `Authorization` header:

```
Authorization: Bearer <jwt_token>
```

JWT tokens expire after 24 hours. Clients should re-authenticate when receiving a `401 Unauthorized` response.

## Response Format

All responses follow the `ApiResponse<T>` wrapper:

```json
{
  "success": true,
  "data": { ... },
  "message": "Optional message"
}
```

### Error Response

```json
{
  "success": false,
  "data": null,
  "message": "Error description"
}
```

### HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request (validation error) |
| 401 | Unauthorized (invalid/expired token) |
| 403 | Forbidden (insufficient permissions) |
| 404 | Not Found |
| 409 | Conflict (duplicate resource) |
| 500 | Internal Server Error |

---

## Auth Endpoints

### POST /api/auth/register

Register a new user account.

**Request Body:**
```json
{
  "email": "player@example.com",
  "password": "securepassword",
  "name": "John Doe"
}
```

**Response (201):**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "user": {
      "id": 1,
      "email": "player@example.com",
      "name": "John Doe",
      "profilePic": null
    }
  },
  "message": "Registration successful"
}
```

**Errors:**
- `400` — Missing required fields
- `409` — Email already registered

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"player@example.com","password":"pass123","name":"John Doe"}'
```

### POST /api/auth/login

Authenticate and receive a JWT token.

**Request Body:**
```json
{
  "email": "player@example.com",
  "password": "securepassword"
}
```

**Response (200):**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "user": {
      "id": 1,
      "email": "player@example.com",
      "name": "John Doe",
      "profilePic": null
    }
  },
  "message": "Login successful"
}
```

**Errors:**
- `401` — Invalid email or password

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"player@example.com","password":"pass123"}'
```

---

## Team Endpoints

### GET /api/teams

List all teams.

**Response (200):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Thunder FC",
      "logoUrl": "https://example.com/logo.png",
      "leader": {
        "id": 1,
        "email": "leader@example.com",
        "name": "John Doe",
        "profilePic": null
      },
      "point": 150,
      "status": "active"
    }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/teams \
  -H "Authorization: Bearer <token>"
```

### GET /api/teams/{id}

Get team detail with players.

**Response (200):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Thunder FC",
    "logoUrl": "https://example.com/logo.png",
    "leader": {
      "id": 1,
      "email": "leader@example.com",
      "name": "John Doe",
      "profilePic": null
    },
    "point": 150,
    "players": [
      {
        "id": 1,
        "name": "John Doe",
        "score": 85,
        "profilePic": null
      },
      {
        "id": 2,
        "name": "Jane Smith",
        "score": 92,
        "profilePic": null
      }
    ]
  },
  "message": null
}
```

```bash
curl http://localhost:8080/api/teams/1 \
  -H "Authorization: Bearer <token>"
```

### POST /api/teams

Create a new team. The authenticated user becomes the team leader.

**Request Body:**
```json
{
  "name": "Thunder FC",
  "playerIds": [2, 3, 4]
}
```

**Response (201):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Thunder FC",
    "logoUrl": null,
    "leader": { "id": 1, "email": "leader@example.com", "name": "John Doe", "profilePic": null },
    "point": 0,
    "status": "active"
  },
  "message": "Team created successfully"
}
```

```bash
curl -X POST http://localhost:8080/api/teams \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Thunder FC","playerIds":[2,3,4]}'
```

### GET /api/teams/{id}/players

List players in a team.

**Response (200):**
```json
{
  "success": true,
  "data": [
    { "id": 1, "name": "John Doe", "score": 85, "profilePic": null },
    { "id": 2, "name": "Jane Smith", "score": 92, "profilePic": null }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/teams/1/players \
  -H "Authorization: Bearer <token>"
```

### GET /api/players

List all available players (users) for team creation.

**Response (200):**
```json
{
  "success": true,
  "data": [
    { "id": 2, "name": "Jane Smith", "score": 92, "profilePic": null },
    { "id": 3, "name": "Bob Wilson", "score": 78, "profilePic": null }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/players \
  -H "Authorization: Bearer <token>"
```

### GET /api/me/teams

List teams for the authenticated user.

**Response (200):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Thunder FC",
      "logoUrl": null,
      "leader": { "id": 1, "email": "me@example.com", "name": "John Doe", "profilePic": null },
      "point": 150,
      "status": "active"
    }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/me/teams \
  -H "Authorization: Bearer <token>"
```

---

## Challenge Endpoints

### GET /api/challenges

List all teams available to challenge (teams that are not the user's own teams).

**Response (200):**
```json
{
  "success": true,
  "data": [
    {
      "id": 2,
      "name": "Lightning United",
      "logoUrl": null,
      "leader": { "id": 3, "name": "Alice", "email": "alice@example.com", "profilePic": null },
      "point": 120,
      "status": "active"
    }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/challenges \
  -H "Authorization: Bearer <token>"
```

### POST /api/challenges

Send a challenge to another team.

**Request Body:**
```json
{
  "challengerTeamId": 1,
  "acceptedTeamId": 2,
  "placeId": 1,
  "reservedTime": "2026-04-15T14:00:00"
}
```

**Response (201):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "challengerTeam": { "id": 1, "name": "Thunder FC", "logoUrl": null, "leader": null, "point": 150, "status": "active" },
    "acceptedTeam": { "id": 2, "name": "Lightning United", "logoUrl": null, "leader": null, "point": 120, "status": "active" },
    "place": { "id": 1, "name": "Central Stadium", "address": "123 Main St", "region": "Downtown", "latitude": 16.8661, "longitude": 96.1951 },
    "reservedTime": "2026-04-15T14:00:00",
    "status": "pending",
    "point": 10
  },
  "message": "Challenge sent successfully"
}
```

```bash
curl -X POST http://localhost:8080/api/challenges \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"challengerTeamId":1,"acceptedTeamId":2,"placeId":1,"reservedTime":"2026-04-15T14:00:00"}'
```

### GET /api/challenges/accepted

Get accepted challenges for the user's teams.

**Query Parameters:**
- `teamId` (optional) — Filter by specific team

**Response (200):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "challengerTeam": { "id": 1, "name": "Thunder FC", "logoUrl": null, "leader": null, "point": 150, "status": "active" },
      "acceptedTeam": { "id": 2, "name": "Lightning United", "logoUrl": null, "leader": null, "point": 120, "status": "active" },
      "place": { "id": 1, "name": "Central Stadium", "address": "123 Main St", "region": "Downtown", "latitude": 16.8661, "longitude": 96.1951 },
      "reservedTime": "2026-04-15T14:00:00",
      "status": "accepted",
      "point": 10
    }
  ],
  "message": null
}
```

```bash
curl "http://localhost:8080/api/challenges/accepted?teamId=1" \
  -H "Authorization: Bearer <token>"
```

### GET /api/challenges/pending

Get pending challenges for the user's teams.

```bash
curl "http://localhost:8080/api/challenges/pending?teamId=1" \
  -H "Authorization: Bearer <token>"
```

### GET /api/challenges/history

Get completed/declined challenge history.

```bash
curl "http://localhost:8080/api/challenges/history?teamId=1" \
  -H "Authorization: Bearer <token>"
```

### POST /api/challenges/{id}/accept

Accept a pending challenge.

**Response (200):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "status": "accepted",
    "...": "full challenge object"
  },
  "message": "Challenge accepted"
}
```

```bash
curl -X POST http://localhost:8080/api/challenges/1/accept \
  -H "Authorization: Bearer <token>"
```

### POST /api/challenges/{id}/decline

Decline a pending challenge.

**Response (200):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "status": "declined",
    "...": "full challenge object"
  },
  "message": "Challenge declined"
}
```

```bash
curl -X POST http://localhost:8080/api/challenges/1/decline \
  -H "Authorization: Bearer <token>"
```

---

## Place Endpoints

### GET /api/places

List all available venues/places.

**Response (200):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Central Stadium",
      "address": "123 Main Street",
      "region": "Downtown",
      "latitude": 16.8661,
      "longitude": 96.1951
    },
    {
      "id": 2,
      "name": "Riverside Arena",
      "address": "456 River Road",
      "region": "Riverside",
      "latitude": 16.8500,
      "longitude": 96.1800
    }
  ],
  "message": null
}
```

```bash
curl http://localhost:8080/api/places \
  -H "Authorization: Bearer <token>"
```
