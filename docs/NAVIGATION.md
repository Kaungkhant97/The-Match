# The Match - App Navigation & Screen Flow

## Navigation Graph

```
                    ┌─────────────┐
                    │    App       │
                    │  (NavHost)   │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │    Auth      │
                    │   Flow       │
                    └──────┬──────┘
                           │
              ┌────────────┼────────────┐
              │                         │
       ┌──────▼──────┐          ┌──────▼──────┐
       │   Login      │◄────────►│  Register    │
       │   Screen     │          │  Screen      │
       └──────┬──────┘          └─────────────┘
              │ (on success)
              │
       ┌──────▼──────────────────────────────────┐
       │           Home Screen                    │
       │         (Bottom Navigation)              │
       │                                          │
       │  ┌──────────┬──────────┬──────────┐     │
       │  │ Profile  │Challenge │  Places   │     │
       │  │  Tab     │  Tab     │   Tab     │     │
       │  └────┬─────┴────┬─────┴────┬─────┘     │
       └───────┼──────────┼──────────┼────────────┘
               │          │          │
        ┌──────▼──────┐   │   ┌──────▼──────┐
        │  Profile     │   │   │  Place List  │
        │  Screen      │   │   │  Screen      │
        │              │   │   └─────────────┘
        │ ┌──────────┐ │   │
        │ │Accepted  │ │   │
        │ │Pending   │ │   │
        │ │History   │ │   │
        │ └────┬─────┘ │   │
        └──────┼───────┘   │
               │           │
        ┌──────▼──────┐    │
        │  Pending     │    │
        │  Confirm     │    │
        │  Screen      │    │
        └─────────────┘    │
                           │
                    ┌──────▼──────┐
                    │  Challenge   │
                    │  List Screen │
                    └──────┬──────┘
                           │ (tap team)
                    ┌──────▼──────┐
                    │  Challenge   │
                    │  Detail      │
                    │  Screen      │
                    └──────┬──────┘
                           │ (challenge)
                    ┌──────▼──────┐
                    │  Place       │
                    │  Select      │
                    │  Screen      │
                    └──────┬──────┘
                           │ (confirm)
                    ┌──────▼──────┐
                    │  Challenge   │
                    │  Sent!       │
                    │  (back home) │
                    └─────────────┘

     Team Creation Flow (from Profile):

        ┌─────────────┐
        │  Team List   │
        │  Screen      │
        └──────┬──────┘
               │ (create new)
        ┌──────▼──────┐
        │  Team        │
        │  Register    │
        │  (enter name)│
        └──────┬──────┘
               │
        ┌──────▼──────┐
        │  Team Create │
        │  (pick       │
        │   players)   │
        └──────┬──────┘
               │ (confirm)
               │ (back home)
               ▼
```

## Route Definitions

| Route | Screen | Parameters | Description |
|-------|--------|------------|-------------|
| `/auth/login` | LoginScreen | — | Email/password login |
| `/auth/register` | RegisterScreen | — | New account registration |
| `/home` | HomeScreen | — | Bottom nav container (3 tabs) |
| `/home/profile` | ProfileScreen | — | User's challenges overview |
| `/home/challenges` | ChallengeListScreen | — | Browse teams to challenge |
| `/home/places` | PlaceListScreen | — | Browse venues |
| `/challenge/{teamId}` | ChallengeDetailScreen | `teamId: Int` | View team detail, initiate challenge |
| `/challenge/place-select` | PlaceSelectScreen | `challengerTeamId: Int, acceptedTeamId: Int` | Pick venue and date |
| `/teams` | TeamListScreen | — | User's teams |
| `/teams/register` | TeamRegisterScreen | — | Enter new team name |
| `/teams/create` | TeamCreateScreen | `teamName: String` | Select players for new team |
| `/pending/{challengeId}` | PendingConfirmScreen | `challengeId: Int` | Accept or decline challenge |
| `/player-challenges` | PlayerChallengeScreen | `filter: String` (accepted/pending/history) | Filtered challenge list |

## Screens Detail

### LoginScreen
- **Purpose:** User authentication
- **ViewModel:** `AuthViewModel`
- **State:** `AuthUiState(isLoading, error, isAuthenticated)`
- **Actions:** Login, navigate to Register
- **Navigation:** On success → HomeScreen

### RegisterScreen
- **Purpose:** New user registration
- **ViewModel:** `AuthViewModel` (shared with Login)
- **State:** Same as LoginScreen
- **Actions:** Register, navigate to Login
- **Navigation:** On success → HomeScreen

### HomeScreen
- **Purpose:** Main navigation container with bottom nav bar
- **Tabs:**
  1. **Profile** (icon: person) → ProfileScreen
  2. **Challenges** (icon: sports) → ChallengeListScreen
  3. **Places** (icon: location) → PlaceListScreen
- **No ViewModel** — pure navigation container

### ProfileScreen
- **Purpose:** Overview of user's challenge activity
- **ViewModel:** `ProfileViewModel`
- **State:** `ProfileUiState(acceptedChallenges, pendingChallenges, historyChallenges, selectedFilter, isLoading, error)`
- **Actions:** Filter by accepted/pending/history, tap challenge for details, navigate to team list
- **Sections:** Filter buttons (Accepted, Pending, History) + challenge list

### ChallengeListScreen
- **Purpose:** Browse available teams to challenge
- **ViewModel:** `ChallengeViewModel`
- **State:** `ChallengeListUiState(teams, isLoading, error)`
- **Actions:** Tap team → ChallengeDetailScreen

### ChallengeDetailScreen
- **Purpose:** View team details and send challenge
- **ViewModel:** `ChallengeViewModel`
- **State:** `ChallengeDetailUiState(teamDetail, isLoading, error)`
- **Actions:** View team players, tap "Challenge" → PlaceSelectScreen

### PlaceSelectScreen
- **Purpose:** Select venue and date for a challenge
- **ViewModel:** `PlaceViewModel`
- **State:** `PlaceSelectUiState(places, selectedPlace, selectedDate, isLoading, isSending, error)`
- **Actions:** Pick place, pick date, confirm → send challenge → HomeScreen

### PlaceListScreen
- **Purpose:** Browse all available venues
- **ViewModel:** `PlaceViewModel`
- **State:** `PlaceListUiState(places, isLoading, error)`
- **Actions:** View place details

### TeamListScreen
- **Purpose:** View user's teams
- **ViewModel:** `TeamViewModel`
- **State:** `TeamListUiState(teams, isLoading, error)`
- **Actions:** Tap team for details, tap "Create Team" → TeamRegisterScreen

### TeamRegisterScreen
- **Purpose:** Enter name for a new team
- **ViewModel:** `TeamViewModel`
- **State:** `TeamRegisterUiState(teamName, isValid)`
- **Actions:** Enter name, next → TeamCreateScreen

### TeamCreateScreen
- **Purpose:** Select players to add to the new team
- **ViewModel:** `TeamViewModel`
- **State:** `TeamCreateUiState(availablePlayers, selectedPlayerIds, isCreating, error)`
- **Actions:** Toggle player selection, confirm → create team → HomeScreen

### PendingConfirmScreen
- **Purpose:** Accept or decline a pending challenge
- **ViewModel:** `PendingViewModel`
- **State:** `PendingUiState(challenge, isLoading, isProcessing, error)`
- **Actions:** Accept → update status, Decline → update status, both → back to profile

### PlayerChallengeScreen
- **Purpose:** View filtered list of challenges (accepted/pending/history)
- **ViewModel:** `ProfileViewModel` (shared)
- **State:** Filtered from ProfileUiState
- **Actions:** Tap pending → PendingConfirmScreen

## Bottom Navigation Tabs

| Tab | Icon | Label | Route | Screen |
|-----|------|-------|-------|--------|
| 1 | `Icons.Default.Person` | Profile | `/home/profile` | ProfileScreen |
| 2 | `Icons.Default.SportsSoccer` | Challenges | `/home/challenges` | ChallengeListScreen |
| 3 | `Icons.Default.Place` | Places | `/home/places` | PlaceListScreen |
