package com.thematch.shared.data.repository

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.model.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.*

class RepositoryTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private fun createMockClient(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): HttpClient {
        return HttpClient(MockEngine { request -> handler(request) }) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    // -- TeamRepository Tests --

    @Test
    fun `TeamRepository getTeams returns teams on success`() = runTest {
        val teams = listOf(Team(id = 1, name = "Alpha"), Team(id = 2, name = "Beta"))
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = teams)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = TeamRepositoryImpl(api)

        val result = repo.getTeams()
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Alpha", result.getOrNull()?.first()?.name)
    }

    @Test
    fun `TeamRepository getTeams returns error on failure`() = runTest {
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse<List<Team>>(success = false, message = "Server error")),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = TeamRepositoryImpl(api)

        val result = repo.getTeams()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Server error") == true)
    }

    @Test
    fun `TeamRepository createTeam returns created team`() = runTest {
        val createdTeam = Team(id = 1, name = "New Team", point = 0, status = "active")
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = createdTeam)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = TeamRepositoryImpl(api)

        val result = repo.createTeam("New Team", listOf(2, 3))
        assertTrue(result.isSuccess)
        assertEquals("New Team", result.getOrNull()?.name)
    }

    @Test
    fun `TeamRepository getMyTeams returns user teams`() = runTest {
        val teams = listOf(Team(id = 5, name = "My Team"))
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = teams)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = TeamRepositoryImpl(api)

        val result = repo.getMyTeams()
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
    }

    // -- ChallengeRepository Tests --

    @Test
    fun `ChallengeRepository getChallengableTeams returns teams`() = runTest {
        val teams = listOf(Team(id = 3, name = "Opponent"))
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = teams)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = ChallengeRepositoryImpl(api)

        val result = repo.getChallengableTeams()
        assertTrue(result.isSuccess)
        assertEquals("Opponent", result.getOrNull()?.first()?.name)
    }

    @Test
    fun `ChallengeRepository sendChallenge returns challenge`() = runTest {
        val challenge = Challenge(
            id = 1,
            challengerTeam = Team(id = 1, name = "A"),
            acceptedTeam = Team(id = 2, name = "B"),
            status = "pending"
        )
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = challenge)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = ChallengeRepositoryImpl(api)

        val result = repo.sendChallenge(1, 2, 1, "2026-04-15T14:00:00")
        assertTrue(result.isSuccess)
        assertEquals("pending", result.getOrNull()?.status)
    }

    @Test
    fun `ChallengeRepository acceptChallenge returns updated challenge`() = runTest {
        val challenge = Challenge(id = 1, challengerTeam = Team(id = 1, name = "A"), status = "accepted")
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = challenge)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = ChallengeRepositoryImpl(api)

        val result = repo.acceptChallenge(1)
        assertTrue(result.isSuccess)
        assertEquals("accepted", result.getOrNull()?.status)
    }

    @Test
    fun `ChallengeRepository declineChallenge returns updated challenge`() = runTest {
        val challenge = Challenge(id = 1, challengerTeam = Team(id = 1, name = "A"), status = "declined")
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = challenge)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = ChallengeRepositoryImpl(api)

        val result = repo.declineChallenge(1)
        assertTrue(result.isSuccess)
        assertEquals("declined", result.getOrNull()?.status)
    }

    @Test
    fun `ChallengeRepository handles network error gracefully`() = runTest {
        val client = createMockClient {
            throw Exception("Network error")
        }
        val api = TheMatchApi(client, "http://test")
        val repo = ChallengeRepositoryImpl(api)

        val result = repo.getAcceptedChallenges()
        assertTrue(result.isFailure)
    }

    // -- PlaceRepository Tests --

    @Test
    fun `PlaceRepository getPlaces returns places`() = runTest {
        val places = listOf(
            Place(id = 1, name = "Stadium", address = "123 Main", region = "Downtown", latitude = 16.8, longitude = 96.2),
            Place(id = 2, name = "Arena", address = "456 Side", region = "East", latitude = 16.9, longitude = 96.3)
        )
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = places)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val repo = PlaceRepositoryImpl(api)

        val result = repo.getPlaces()
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    // -- AuthRepository Tests --

    @Test
    fun `AuthRepository login stores token`() = runTest {
        val authResponse = AuthResponse(
            token = "test-jwt-token",
            user = User(id = 1, email = "test@test.com", name = "Test")
        )
        val client = createMockClient {
            respond(
                content = json.encodeToString(ApiResponse(success = true, data = authResponse)),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = TheMatchApi(client, "http://test")
        val tokenStorage = InMemoryTokenStorage()
        val repo = AuthRepositoryImpl(api, tokenStorage)

        val result = repo.login("test@test.com", "pass")
        assertTrue(result.isSuccess)
        assertEquals("test-jwt-token", tokenStorage.getToken())
        assertTrue(repo.isLoggedIn())
    }

    @Test
    fun `AuthRepository clearToken clears session`() = runTest {
        val tokenStorage = InMemoryTokenStorage()
        tokenStorage.saveToken("some-token")

        val client = createMockClient {
            respond("", status = HttpStatusCode.OK)
        }
        val api = TheMatchApi(client, "http://test")
        val repo = AuthRepositoryImpl(api, tokenStorage)

        assertTrue(repo.isLoggedIn())
        repo.clearToken()
        assertFalse(repo.isLoggedIn())
        assertNull(tokenStorage.getToken())
    }

    @Test
    fun `InMemoryTokenStorage works correctly`() {
        val storage = InMemoryTokenStorage()

        assertNull(storage.getToken())
        assertFalse(storage.hasToken())

        storage.saveToken("abc123")
        assertEquals("abc123", storage.getToken())
        assertTrue(storage.hasToken())

        storage.clearToken()
        assertNull(storage.getToken())
        assertFalse(storage.hasToken())
    }
}
