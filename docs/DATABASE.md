# The Match - Database Schema

## Overview

The server uses **Exposed ORM** with **H2** (development) or **PostgreSQL** (production) as the database engine. All tables use auto-incrementing integer primary keys and UTC timestamps.

## Entity Relationship Diagram

```
┌──────────────┐       ┌───────────────────┐       ┌──────────────┐
│    Users      │       │   TeamPlayers      │       │    Teams      │
│──────────────│       │───────────────────│       │──────────────│
│ id (PK)      │◄──┐   │ id (PK)           │   ┌──►│ id (PK)      │
│ email        │   │   │ teamId (FK)───────│───┘   │ name         │
│ passwordHash │   └───│─playerId (FK)     │       │ logoUrl      │
│ name         │       └───────────────────┘       │ leaderId(FK)─│──┐
│ profilePic   │                                   │ point        │  │
│ createdAt    │◄──────────────────────────────────│ status       │  │
└──────────────┘                                   │ createdAt    │  │
       ▲                                           └──────────────┘  │
       │                                                  ▲          │
       └──────────────────────────────────────────────────┼──────────┘
                                                          │
┌──────────────┐       ┌───────────────────┐              │
│    Places     │       │   Challenges       │              │
│──────────────│       │───────────────────│              │
│ id (PK)      │◄──┐   │ id (PK)           │              │
│ name         │   │   │ challengerTeamId──│──────────────┘
│ address      │   │   │ acceptedTeamId────│──────────────┘
│ region       │   └───│─placeId (FK)      │
│ latitude     │       │ reservedTime      │
│ longitude    │       │ status            │
│ createdAt    │       │ point             │
│ updatedAt    │       │ createdAt         │
└──────────────┘       └───────────────────┘
```

## Tables

### Users

Stores registered user accounts.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT | Unique user ID |
| `email` | VARCHAR(255) | NOT NULL, UNIQUE | Login email |
| `password_hash` | VARCHAR(255) | NOT NULL | BCrypt hashed password |
| `name` | VARCHAR(100) | NOT NULL | Display name |
| `profile_pic` | VARCHAR(500) | NULLABLE | Profile picture URL |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Account creation time |

**Indexes:**
- `idx_users_email` — UNIQUE on `email` (fast login lookup)

### Teams

Stores team information.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT | Unique team ID |
| `name` | VARCHAR(100) | NOT NULL | Team name |
| `logo_url` | VARCHAR(500) | NULLABLE | Team logo URL |
| `leader_id` | INTEGER | NOT NULL, FK → Users(id) | Team creator/leader |
| `point` | INTEGER | NOT NULL, DEFAULT 0 | Team score points |
| `status` | VARCHAR(20) | NOT NULL, DEFAULT 'active' | active, inactive |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Team creation time |

**Indexes:**
- `idx_teams_leader` — on `leader_id` (find user's teams)

### TeamPlayers

Join table for many-to-many relationship between Teams and Users (players).

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT | Row ID |
| `team_id` | INTEGER | NOT NULL, FK → Teams(id) | Team reference |
| `player_id` | INTEGER | NOT NULL, FK → Users(id) | Player (user) reference |

**Indexes:**
- `idx_teamplayers_team` — on `team_id` (find team's players)
- `idx_teamplayers_player` — on `player_id` (find player's teams)
- UNIQUE constraint on (`team_id`, `player_id`) — prevent duplicate membership

### Challenges

Stores match challenges between teams.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT | Unique challenge ID |
| `challenger_team_id` | INTEGER | NOT NULL, FK → Teams(id) | Team that sent the challenge |
| `accepted_team_id` | INTEGER | NOT NULL, FK → Teams(id) | Team being challenged |
| `place_id` | INTEGER | NULLABLE, FK → Places(id) | Venue for the match |
| `reserved_time` | TIMESTAMP | NULLABLE | Scheduled match time |
| `status` | VARCHAR(20) | NOT NULL, DEFAULT 'pending' | pending, accepted, declined, completed |
| `point` | INTEGER | NOT NULL, DEFAULT 10 | Points at stake |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Challenge creation time |

**Indexes:**
- `idx_challenges_challenger` — on `challenger_team_id`
- `idx_challenges_accepted` — on `accepted_team_id`
- `idx_challenges_status` — on `status` (filter by status)

**Status values:**
- `pending` — Challenge sent, awaiting response
- `accepted` — Challenge accepted by the other team
- `declined` — Challenge declined
- `completed` — Match has been played

### Places

Stores venue/location information.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTO_INCREMENT | Unique place ID |
| `name` | VARCHAR(200) | NOT NULL | Venue name |
| `address` | VARCHAR(500) | NOT NULL | Street address |
| `region` | VARCHAR(100) | NOT NULL | City/region |
| `latitude` | DOUBLE | NOT NULL | GPS latitude |
| `longitude` | DOUBLE | NOT NULL | GPS longitude |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Record creation time |
| `updated_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Last update time |

## Seed Data

The server will seed the following places on first startup:

| Name | Address | Region | Lat | Lng |
|------|---------|--------|-----|-----|
| Central Stadium | 123 Main Street | Downtown | 16.8661 | 96.1951 |
| Riverside Arena | 456 River Road | Riverside | 16.8500 | 96.1800 |
| North Park Field | 789 Park Avenue | Northside | 16.8800 | 96.2000 |
| South Sports Complex | 321 Sports Drive | Southside | 16.8400 | 96.1700 |
| University Ground | 654 Campus Road | University | 16.8550 | 96.1900 |

## Exposed ORM Table Definitions

```kotlin
// Example: Teams table in Exposed
object Teams : IntIdTable("teams") {
    val name = varchar("name", 100)
    val logoUrl = varchar("logo_url", 500).nullable()
    val leaderId = reference("leader_id", Users)
    val point = integer("point").default(0)
    val status = varchar("status", 20).default("active")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}
```

## Migration Strategy

- Development uses H2 in-memory database with `SchemaUtils.create()` on startup
- Production should use PostgreSQL with Flyway or manual migration scripts
- Tables are created automatically if they don't exist (`SchemaUtils.createMissingTablesAndColumns()`)
