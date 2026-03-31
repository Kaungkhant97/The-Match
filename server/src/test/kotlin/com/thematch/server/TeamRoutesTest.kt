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

class TeamRoutesTest {

    private fun ApplicationTestBuilder.createJsonClient() = createClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private suspend fun registerAndGetToken(
        client: io.ktor.client.HttpClient,
        email: String = "team-user@example.com",
        name: String = "Team User"
    ): String {
        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = email, password = "password123", name = name))
        }
        val body = response.body<ApiResponse<AuthResponse>>()
        return body.data!!.token
    }

    @Test
    fun `get teams requires authentication`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.get("/api/teams")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `get teams returns empty list initially`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        val response = client.get("/api/teams") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Team>>>()
        assertTrue(body.success)
        assertNotNull(body.data)
        assertTrue(body.data!!.isEmpty())
    }

    @Test
    fun `create team and retrieve it`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        // Create team
        val createResponse = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "Thunder FC", playerIds = emptyList()))
        }

        assertEquals(HttpStatusCode.Created, createResponse.status)
        val createBody = createResponse.body<ApiResponse<Team>>()
        assertTrue(createBody.success)
        assertEquals("Thunder FC", createBody.data!!.name)

        // Get all teams
        val listResponse = client.get("/api/teams") {
            bearerAuth(token)
        }

        val listBody = listResponse.body<ApiResponse<List<Team>>>()
        assertTrue(listBody.success)
        assertEquals(1, listBody.data!!.size)
        assertEquals("Thunder FC", listBody.data!!.first().name)
    }

    @Test
    fun `create team rejects empty name`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        val response = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "", playerIds = emptyList()))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `get team detail by id`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        // Create team
        val createBody = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "Detail Team", playerIds = emptyList()))
        }.body<ApiResponse<Team>>()

        val teamId = createBody.data!!.id

        // Get detail
        val response = client.get("/api/teams/$teamId") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<TeamDetail>>()
        assertTrue(body.success)
        assertEquals("Detail Team", body.data!!.name)
        assertTrue(body.data!!.players.isNotEmpty()) // leader is auto-added as player
    }

    @Test
    fun `get team detail returns 404 for nonexistent team`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        val response = client.get("/api/teams/99999") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `get team players`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        // Create team
        val createBody = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "Players Team", playerIds = emptyList()))
        }.body<ApiResponse<Team>>()

        val teamId = createBody.data!!.id

        val response = client.get("/api/teams/$teamId/players") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Player>>>()
        assertTrue(body.success)
        assertTrue(body.data!!.isNotEmpty()) // leader is a player
    }

    @Test
    fun `get my teams returns user's teams`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        // Create team
        client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "My Team", playerIds = emptyList()))
        }

        val response = client.get("/api/me/teams") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Team>>>()
        assertTrue(body.success)
        assertEquals(1, body.data!!.size)
        assertEquals("My Team", body.data!!.first().name)
    }

    @Test
    fun `get all players`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        val response = client.get("/api/players") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Player>>>()
        assertTrue(body.success)
        assertTrue(body.data!!.isNotEmpty())
    }

    @Test
    fun `create team with additional players`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = registerAndGetToken(client)

        // Register another user to be a player
        registerAndGetToken(client, "player2@example.com", "Player Two")

        // Create team with player 2
        val createResponse = client.post("/api/teams") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(CreateTeamRequest(name = "Full Team", playerIds = listOf(2)))
        }

        assertEquals(HttpStatusCode.Created, createResponse.status)

        // Verify team has 2 players
        val createBody = createResponse.body<ApiResponse<Team>>()
        val teamId = createBody.data!!.id

        val detailResponse = client.get("/api/teams/$teamId") {
            bearerAuth(token)
        }
        val detail = detailResponse.body<ApiResponse<TeamDetail>>()
        assertEquals(2, detail.data!!.players.size)
    }
}
