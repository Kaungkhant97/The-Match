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

class PlaceRoutesTest {

    private fun ApplicationTestBuilder.createJsonClient() = createClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private suspend fun getToken(client: io.ktor.client.HttpClient): String {
        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "place-user@example.com", password = "pass123", name = "Place User"))
        }
        return response.body<ApiResponse<AuthResponse>>().data!!.token
    }

    @Test
    fun `get places requires authentication`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.get("/api/places")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `get places returns seeded data`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = getToken(client)

        val response = client.get("/api/places") {
            bearerAuth(token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<List<Place>>>()
        assertTrue(body.success)
        assertNotNull(body.data)
        assertEquals(5, body.data!!.size)
    }

    @Test
    fun `seeded places have correct data`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = getToken(client)

        val response = client.get("/api/places") {
            bearerAuth(token)
        }

        val places = response.body<ApiResponse<List<Place>>>().data!!
        val centralStadium = places.find { it.name == "Central Stadium" }
        assertNotNull(centralStadium)
        assertEquals("123 Main Street", centralStadium.address)
        assertEquals("Downtown", centralStadium.region)
        assertEquals(16.8661, centralStadium.latitude, 0.001)
        assertEquals(96.1951, centralStadium.longitude, 0.001)
    }

    @Test
    fun `all seeded places are present`() = testApplication {
        application { module() }
        val client = createJsonClient()
        val token = getToken(client)

        val response = client.get("/api/places") {
            bearerAuth(token)
        }

        val placeNames = response.body<ApiResponse<List<Place>>>().data!!.map { it.name }
        assertTrue("Central Stadium" in placeNames)
        assertTrue("Riverside Arena" in placeNames)
        assertTrue("North Park Field" in placeNames)
        assertTrue("South Sports Complex" in placeNames)
        assertTrue("University Ground" in placeNames)
    }
}
