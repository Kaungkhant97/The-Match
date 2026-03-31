# The Match - Developer Setup Guide

## Prerequisites

### Required

| Tool | Version | Purpose |
|------|---------|---------|
| **JDK** | 17+ | Kotlin/JVM compilation |
| **Android Studio** | Ladybug+ (2024.2+) | Android development, Compose preview |
| **Android SDK** | API 35 | Target SDK |

### Optional (for iOS development)

| Tool | Version | Purpose |
|------|---------|---------|
| **Xcode** | 15+ | iOS build and run |
| **CocoaPods** | Latest | iOS dependency management |
| macOS | Required for iOS | Apple requirement |

## Project Structure

```
The-Match/
├── server/         # Ktor backend API (JVM)
├── shared/         # KMP shared business logic (Android + iOS)
├── composeApp/     # Compose Multiplatform UI (Android + iOS)
├── iosApp/         # iOS Xcode project wrapper
├── gradle/         # Version catalog + wrapper
└── docs/           # Documentation
```

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Kaungkhant97/The-Match.git
cd The-Match
```

### 2. Run the Backend Server

```bash
# Start the Ktor server on localhost:8080
./gradlew :server:run
```

The server starts with an H2 in-memory database and seeds initial place data. API is available at `http://localhost:8080/api`.

Verify it's running:
```bash
curl http://localhost:8080/api/places
```

### 3. Run the Android App

1. Open the project in Android Studio
2. Select the `composeApp` run configuration
3. Choose an emulator or connected device (API 24+)
4. Click Run

Or from command line:
```bash
./gradlew :composeApp:assembleDebug
```

### 4. Run the iOS App (macOS only)

```bash
# Build the shared framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Open in Xcode
open iosApp/iosApp.xcodeproj
```

Then run from Xcode on a simulator or device.

## Configuration

### Server Base URL

The client base URL is configured in:
```
shared/src/commonMain/kotlin/com/thematch/shared/util/Constants.kt
```

Default values:
- Android emulator: `http://10.0.2.2:8080/api` (maps to host localhost)
- iOS simulator: `http://localhost:8080/api`
- Physical device: Use your machine's local IP address

### Server Port

Configure in `server/src/main/resources/application.conf`:
```hocon
ktor {
    deployment {
        port = 8080
    }
}
```

### Database

- **Development:** H2 in-memory (no setup needed, resets on restart)
- **Production:** Set PostgreSQL connection in `application.conf`:
  ```hocon
  database {
      url = "jdbc:postgresql://localhost:5432/thematch"
      user = "postgres"
      password = "your_password"
  }
  ```

## Build Commands

| Command | Description |
|---------|-------------|
| `./gradlew :server:run` | Start backend server |
| `./gradlew :composeApp:assembleDebug` | Build Android debug APK |
| `./gradlew :shared:allTests` | Run shared module tests |
| `./gradlew :server:test` | Run server tests |
| `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64` | Build iOS framework |
| `./gradlew clean` | Clean all build artifacts |
| `./gradlew build` | Build everything |

## Troubleshooting

### Gradle sync fails
- Ensure JDK 17+ is set in Android Studio → Settings → Build → Gradle → JDK
- Run `./gradlew --stop` to kill daemon, then retry

### Android emulator can't reach server
- Use `10.0.2.2` instead of `localhost` for Android emulator
- Ensure server is running before launching app
- Check firewall settings

### iOS build fails
- Ensure Xcode command line tools are installed: `xcode-select --install`
- Clean and rebuild: `./gradlew clean :shared:linkDebugFrameworkIosSimulatorArm64`

### Port already in use
- Kill existing process: `lsof -i :8080 | grep LISTEN` then `kill <PID>`
- Or change port in `application.conf`
