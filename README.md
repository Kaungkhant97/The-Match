# The Match

A sports team matching platform built with Kotlin Multiplatform. Create teams, challenge opponents, pick venues, and manage match outcomes.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Ktor Server, Exposed ORM, H2/PostgreSQL, JWT Auth |
| **Shared Logic** | Kotlin Multiplatform, Ktor Client, SQLDelight, Koin |
| **UI** | Compose Multiplatform (Android + iOS) |
| **Serialization** | Kotlin Serialization |
| **Async** | Kotlin Coroutines + Flow |

## Modules

```
server/       → Ktor REST API backend
shared/       → KMP shared business logic, networking, data models
composeApp/   → Compose Multiplatform UI (Android + iOS)
iosApp/       → iOS Xcode project wrapper
```

## Features

- **Authentication** — Email/password registration and login with JWT
- **Teams** — Create teams, add players, view team details
- **Challenges** — Send challenges to other teams, accept or decline incoming challenges
- **Places** — Browse venues for matches, select date and location
- **Profile** — View accepted, pending, and past challenges

## Quick Start

### 1. Start the server

```bash
./gradlew :server:run
```

Server starts at `http://localhost:8080`. Verify: `curl http://localhost:8080/api/places`

### 2. Run Android app

```bash
./gradlew :composeApp:assembleDebug
```

Or open in Android Studio and run the `composeApp` configuration.

### 3. Run iOS app (macOS only)

```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
open iosApp/iosApp.xcodeproj
```

## Documentation

- [Architecture](docs/ARCHITECTURE.md) — System design, module structure, data flow
- [API Specification](docs/API.md) — All REST endpoints with examples
- [Database Schema](docs/DATABASE.md) — Tables, relationships, ER diagram
- [Navigation](docs/NAVIGATION.md) — App screen flow and routes
- [Setup Guide](docs/SETUP.md) — Development environment setup

## Architecture

```
Android/iOS App
    └── composeApp (Compose Multiplatform UI + ViewModels)
            └── shared (Repositories + Ktor Client + SQLDelight)
                    └── server (Ktor API + Exposed ORM + Database)
```

MVVM + Clean Architecture with unidirectional data flow:
- **ViewModels** expose `StateFlow<UiState>` to Compose screens
- **Repositories** abstract data sources (remote API + local cache)
- **Ktor Client** communicates with Ktor Server via REST/JSON

## Requirements

- JDK 17+
- Android Studio Ladybug+ (2024.2+)
- Android SDK API 35
- Xcode 15+ (iOS only, macOS required)
