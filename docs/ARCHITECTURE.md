# The Match - System Architecture

## Overview

The Match is a full-stack Kotlin sports team matching platform. Users can create teams, challenge other teams to matches, select venues, and manage challenge outcomes. The system is built as a Kotlin Multiplatform (KMP) project with a Ktor backend server and Compose Multiplatform clients for Android and iOS.

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────┐
│                      Clients                             │
│                                                          │
│  ┌──────────────────┐       ┌──────────────────┐        │
│  │   Android App     │       │    iOS App        │        │
│  │  (Compose MP)     │       │  (Compose MP)     │        │
│  │                   │       │                   │        │
│  │  ┌─────────────┐ │       │  ┌─────────────┐ │        │
│  │  │ MainActivity │ │       │  │MainView     │ │        │
│  │  │             │ │       │  │Controller   │ │        │
│  │  └──────┬──────┘ │       │  └──────┬──────┘ │        │
│  └─────────┼────────┘       └─────────┼────────┘        │
│            │                          │                  │
│            └──────────┬───────────────┘                  │
│                       │                                  │
│            ┌──────────▼──────────┐                       │
│            │   composeApp Module  │                       │
│            │  (Shared UI Layer)   │                       │
│            │                     │                       │
│            │  Screens + ViewModels│                       │
│            │  Navigation          │                       │
│            │  Theme + Components  │                       │
│            └──────────┬──────────┘                       │
│                       │                                  │
│            ┌──────────▼──────────┐                       │
│            │   shared Module      │                       │
│            │  (Business Logic)    │                       │
│            │                     │                       │
│            │  Repositories        │                       │
│            │  Ktor Client API     │                       │
│            │  SQLDelight Cache    │                       │
│            │  Koin DI             │                       │
│            └──────────┬──────────┘                       │
│                       │                                  │
└───────────────────────┼──────────────────────────────────┘
                        │ HTTPS / REST
                        │
┌───────────────────────▼──────────────────────────────────┐
│                   Ktor Server                             │
│                                                          │
│  ┌─────────────┐  ┌──────────┐  ┌──────────────────┐    │
│  │   Routes     │  │ Security │  │  Content          │    │
│  │             │  │  (JWT)   │  │  Negotiation      │    │
│  │ Auth        │  └──────────┘  │  (JSON)           │    │
│  │ Teams       │                └──────────────────┘    │
│  │ Challenges  │                                        │
│  │ Places      │  ┌──────────────────────────────┐      │
│  └──────┬──────┘  │        DAOs                   │      │
│         │         │  UserDao, TeamDao,             │      │
│         └────────►│  ChallengeDao, PlaceDao        │      │
│                   └──────────┬───────────────────┘      │
│                              │                           │
│                   ┌──────────▼───────────────────┐      │
│                   │    Exposed ORM Tables          │      │
│                   │  Users, Teams, TeamPlayers,    │      │
│                   │  Challenges, Places            │      │
│                   └──────────┬───────────────────┘      │
│                              │                           │
│                   ┌──────────▼───────────────────┐      │
│                   │    H2 / PostgreSQL Database    │      │
│                   └──────────────────────────────┘      │
└──────────────────────────────────────────────────────────┘
```

## Module Dependency Graph

```
iosApp ──────────► composeApp ──────────► shared
                       │                     │
                       │                     ├── Ktor Client
                       │                     ├── SQLDelight
                       │                     ├── Koin
                       │                     └── Kotlin Serialization
                       │
                       ├── Compose Multiplatform
                       ├── Compose Navigation
                       ├── Coil (Image Loading)
                       └── Koin Compose

server ──────────► shared (models only)
    │
    ├── Ktor Server
    ├── Exposed ORM
    ├── BCrypt
    └── JWT Auth
```

### Module Descriptions

| Module | Type | Purpose |
|--------|------|---------|
| **shared** | KMP Library | Business logic, data models, networking, local storage, DI |
| **composeApp** | KMP Application | Compose Multiplatform UI, ViewModels, navigation, theme |
| **server** | JVM Application | Ktor REST API server, database, authentication |
| **iosApp** | Xcode Project | iOS app shell hosting Compose Multiplatform UI |

## Clean Architecture Layers

### shared Module

```
┌─────────────────────────────────────────┐
│           Domain Layer                   │
│                                          │
│  Repository Interfaces                   │
│  (AuthRepository, TeamRepository, etc.)  │
│                                          │
│  Shared Models (@Serializable)           │
│  (User, Team, Player, Challenge, Place)  │
└────────────────┬────────────────────────┘
                 │ implements
┌────────────────▼────────────────────────┐
│            Data Layer                    │
│                                          │
│  ┌─────────────────┐  ┌──────────────┐  │
│  │  Remote (Ktor)   │  │ Local        │  │
│  │  TheMatchApi     │  │ (SQLDelight) │  │
│  └────────┬────────┘  └──────┬───────┘  │
│           │                  │           │
│  ┌────────▼──────────────────▼───────┐  │
│  │     Repository Implementations     │  │
│  │  (Single source of truth logic)    │  │
│  └────────────────────────────────────┘  │
└──────────────────────────────────────────┘
```

### composeApp Module

```
┌──────────────────────────────────────────┐
│        Presentation Layer                 │
│                                           │
│  ┌─────────────┐   ┌──────────────────┐  │
│  │  Screens     │   │  ViewModels       │  │
│  │  (Compose)   │◄──│  (StateFlow)      │  │
│  └─────────────┘   └────────┬─────────┘  │
│                              │            │
│                    ┌─────────▼─────────┐  │
│                    │  Repositories      │  │
│                    │  (from shared)     │  │
│                    └───────────────────┘  │
└──────────────────────────────────────────┘
```

## Data Flow

### API Request Flow (Client → Server)

```
User Action (tap button)
    │
    ▼
Screen Composable (event callback)
    │
    ▼
ViewModel (coroutine launch)
    │
    ▼
Repository Interface (domain layer)
    │
    ▼
Repository Implementation (data layer)
    │
    ├──► SQLDelight (check cache)
    │
    ▼
TheMatchApi (Ktor HttpClient)
    │
    ▼ HTTPS POST/GET
    │
Ktor Server (route handler)
    │
    ▼
JWT Authentication (verify token)
    │
    ▼
DAO (database query via Exposed)
    │
    ▼
Database (H2/PostgreSQL)
    │
    ▼ Response
    │
Ktor Server (serialize response)
    │
    ▼ JSON
    │
TheMatchApi (deserialize)
    │
    ▼
Repository (cache to SQLDelight, return)
    │
    ▼
ViewModel (update StateFlow<UiState>)
    │
    ▼
Screen Composable (recompose with new state)
```

### Authentication Flow

```
┌──────────┐     POST /api/auth/register      ┌──────────┐
│  Client   │ ──────────────────────────────► │  Server   │
│           │   {email, password, name}        │           │
│           │                                  │  Hash pw  │
│           │     {token, user}                │  Store    │
│           │ ◄────────────────────────────── │  Gen JWT  │
│           │                                  │           │
│  Store    │     POST /api/auth/login         │           │
│  token    │ ──────────────────────────────► │  Verify   │
│  locally  │   {email, password}              │  pw hash  │
│           │                                  │  Gen JWT  │
│           │     {token, user}                │           │
│           │ ◄────────────────────────────── │           │
│           │                                  │           │
│  Attach   │     GET /api/teams               │           │
│  Bearer   │ ──────────────────────────────► │  Verify   │
│  token    │   Authorization: Bearer <jwt>    │  JWT      │
│           │                                  │  Process  │
│           │     {teams: [...]}               │           │
│           │ ◄────────────────────────────── │           │
└──────────┘                                  └──────────┘
```

## Technology Stack

| Layer | Technology | Rationale |
|-------|-----------|-----------|
| **Language** | Kotlin 2.1.x | Single language for all layers, KMP support |
| **Build** | Gradle 8.9+ / Kotlin DSL | Modern build system with version catalogs |
| **Server Framework** | Ktor 3.x | Kotlin-native, lightweight, coroutine-based |
| **Server ORM** | Exposed 0.56.x | Kotlin-native SQL framework, type-safe |
| **Server DB** | H2 (dev) / PostgreSQL (prod) | Embedded for dev, scalable for prod |
| **Server Auth** | JWT + BCrypt | Stateless auth, industry standard hashing |
| **Client UI** | Compose Multiplatform 1.7.x | Shared UI across Android + iOS |
| **Client Networking** | Ktor Client 3.x | KMP-compatible, shares serialization with server |
| **Client DI** | Koin 4.x | KMP-compatible, simple to configure |
| **Client Cache** | SQLDelight 2.x | KMP-compatible, type-safe SQL |
| **Client Images** | Coil 3.x | KMP-compatible, Compose integration |
| **Serialization** | Kotlin Serialization 1.7.x | Shared between server and client, no reflection |
| **Async** | Kotlin Coroutines 1.9.x + Flow | Structured concurrency, reactive streams |
| **Navigation** | Compose Navigation | Type-safe routes, deep linking support |

## KMP Target Platforms

| Platform | Source Set | Engine/Framework |
|----------|-----------|-----------------|
| Android | `androidMain` | Ktor OkHttp engine, SQLDelight Android driver |
| iOS | `iosMain` | Ktor Darwin engine, SQLDelight Native driver |
| Server | JVM only | Ktor Netty engine, Exposed JDBC |

## ViewModel State Pattern

All ViewModels follow the same UiState pattern:

```kotlin
data class ScreenUiState(
    val data: T = default,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ScreenViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenUiState())
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getData()
                .onSuccess { data -> _uiState.update { it.copy(data = data, isLoading = false) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
        }
    }
}
```
