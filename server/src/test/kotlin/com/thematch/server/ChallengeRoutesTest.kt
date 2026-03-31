package com.thematch.server

import com.thematch.shared.model.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.*

class ChallengeRoutesTest {

    private fun ApplicationTestBuilder.createJsonClient() = createClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private suspend fun registerAndGetToken(
        client: io.ktor.client.HttpClient,
        email: String,
        name: String
    ): String {
        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = email, password = "password123", name = name))
        }
        return response.body<ApiResponse<AuthResponse>>().data!!.token
    }

    private suspend fun createTeam(
        client: io.ktor.client.HttpClient,
        token: String,
        name: String,
        playerIds: List<Int> = emptyList()
    ): Team {
        val response = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = name, playerIds = playerIds))
        }
        return response.body<ApiResponse<Team>>().data!!
    }

    @Test
    fun `get challengable teams excludes own teams`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val token1 = registerAndGetToken(client, "user1@test.com", "User 1")
        val token2 = registerAndGetToken(client, "user2@test.com", "User 2")

        createTeam(client, token1, "Team Alpha")
        createTeam(client, token2, "Team Beta")

        // User 1 should only see Team Beta
        val response = client.get("/api/challenges") {
            bearerAuth(token1)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Team>>>()
        assertTrue(body.success)
        assertEquals(1, body.data!!.size)
        assertEquals("Team Beta", body.data!!.first().name)
    }

    @Test
    fun `send challenge creates pending challenge`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val token1 = registerAndGetToken(client, "ch1@test.com", "Challenger")
        val token2 = registerAndGetToken(client, "ch2@test.com", "Opponent")

        val team1 = createTeam(client, token1, "Challengers")
        val team2 = createTeam(client, token2, "Opponents")

        val response = client.post("/api/challenges") {
            bearerAuth(token1)
            contentType(ContentType.Application.Json)
            setBody(CreateChallengeRequest(
                challengerTeamId = team1.id,
                acceptedTeamId = team2.id,
                placeId = 1,
                reservedTime = "2026-04-15T14:00:00"
            ))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val body = response.body<ApiResponse<Challenge>>()
        assertTrue(body.success)
        assertEquals("pending", body.data!!.status)
        assertEquals(team1.id, body.data!!.challengerTeam.id)
        assertEquals(team2.id, body.data!!.acceptedTeam?.id)
    }

    @Test
    fun `get pending challenges`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val token1 = registerAndGetToken(client, "p1@test.com", "User A")
        val token2 = registerAndGetToken(client, "p2@test.com", "User B")

        val team1 = createTeam(client, token1, "Pending A")
        val team2 = createTeam(client, token2, "Pending B")

        // Send challenge
        client.post("/api/challenges") {
            bearerAuth(token1)
            contentType(ContentType.Application.Json)
            setBody(CreateChallengeRequest(team1.id, team2.id, 1, "2026-05-01T10:00:00"))
        }

        // Check pending for user 2 (opponent)
        val response = client.get("/api/challenges/pending") {
            bearerAuth(token2)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Challenge>>>()
        assertTrue(body.success)
        assertEquals(1, body.data!!.size)
        assertEquals("pending", body.data!!.first().status)
    }

    @Test
    fun `accept challenge changes status`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val token1 = registerAndGetToken(client, "acc1@test.com", "Accepter A")
        val token2 = registerAndGetToken(client, "acc2@test.com", "Accepter B")

        val team1 = createTeam(client, token1, "Accept Team A")
        val team2 = createTeam(client, token2, "Accept Team B")

        // Send challenge
        val challengeResp = client.post("/api/challenges") {
            bearerAuth(token1)
            contentType(ContentType.Application.Json)
            setBody(CreateChallengeRequest(team1.id, team2.id, 1, "2026-05-01T10:00:00"))
        }
        val challengeId = challengeResp.body<ApiResponse<Challenge>>().data!!.id

        // Accept
        val acceptResp = client.post("/api/challenges/$challengeId/accept") {
            bearerAuth(token2)
        }

        assertEquals(HttpStatusCode.OK, acceptResp.status)
        val acceptBody = acceptResp.body<ApiResponse<Challenge>>()
        assertTrue(acceptBody.success)
        assertEquals("accepted", acceptBody.data!!.status)

        // Verify it shows in accepted list
        val acceptedList = client.get("/api/challenges/accepted") {
            bearerAuth(token1)
        }.body<ApiResponse<List<Challenge>>>()

        assertEquals(1, acceptedList.data!!.size)
        assertEquals("accepted", acceptedList.data!!.first().status)
    }

    @Test
    fun `decline challenge changes status`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val token1 = registerAndGetToken(client, "dec1@test.com", "Decliner A")
        val token2 = registerAndGetToken(client, "dec2@test.com", "Decliner B")

        val team1 = createTeam(client, token1, "Decline Team A")
        val team2 = createTeam(client, token2, "Decline Team B")

        val challengeResp = client.post("/api/challenges") {
            bearerAuth(token1)
            contentType(ContentType.Application.Json)
            setBody(CreateChallengeRequest(team1.id, team2.id, 2, "2026-06-01T10:00:00"))
        }
        val challengeId = challengeResp.body<ApiResponse<Challenge>>().data!!.id

        val declineResp = client.post("/api/challenges/$challengeId/decline") {
            bearerAuth(token2)
        }

        assertEquals(HttpStatusCode.OK, declineResp.status)
        val body = declineResp.body<ApiResponse<Challenge>>()
        assertEquals("declined", body.data!!.status)

        // Verify it shows in history
        val historyList = client.get("/api/challenges/history") {
            bearerAuth(token1)
        }.body<ApiResponse<List<Challenge>>>()

        assertEquals(1, historyList.data!!.size)
    }

    @Test
    fun `accept nonexistent challenge returns 404`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client, "nf@test.com", "Not Found")

        val response = client.post("/api/challenges/99999/accept") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `accepted challenges list is empty initially`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client, "empty@test.com", "Empty User")

        val response = client.get("/api/challenges/accepted") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Challenge>>>()
        assertTrue(body.data!!.isEmpty())
    }
}
