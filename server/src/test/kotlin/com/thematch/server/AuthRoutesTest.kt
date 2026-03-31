package com.thematch.server

import com.thematch.shared.model.ApiResponse
import com.thematch.shared.model.AuthRequest
import com.thematch.shared.model.AuthResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.*

class AuthRoutesTest {

    private fun ApplicationTestBuilder.createJsonClient() = createClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @Test
    fun `register creates new user and returns token`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "test@example.com", password = "password123", name = "Test User"))
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val body = response.body<ApiResponse<AuthResponse>>()
        assertTrue(body.success)
        assertNotNull(body.data)
        assertEquals("test@example.com", body.data!!.user.email)
        assertEquals("Test User", body.data!!.user.name)
        assertTrue(body.data!!.token.isNotBlank())
    }

    @Test
    fun `register rejects duplicate email`() = testApplication {
        application { module() }
        val client = createJsonClient()

        // Register first
        client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "dup@example.com", password = "pass123", name = "User"))
        }

        // Try duplicate
        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "dup@example.com", password = "pass456", name = "User 2"))
        }

        assertEquals(HttpStatusCode.Conflict, response.status)
        val body = response.body<ApiResponse<AuthResponse>>()
        assertFalse(body.success)
        assertEquals("Email already registered", body.message)
    }

    @Test
    fun `register rejects empty fields`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "", password = "pass123", name = "Name"))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `register rejects missing name`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "test@example.com", password = "pass123"))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `login succeeds with correct credentials`() = testApplication {
        application { module() }
        val client = createJsonClient()

        // Register first
        client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "login@example.com", password = "mypassword", name = "Login User"))
        }

        // Login
        val response = client.post("/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "login@example.com", password = "mypassword"))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ApiResponse<AuthResponse>>()
        assertTrue(body.success)
        assertNotNull(body.data)
        assertEquals("login@example.com", body.data!!.user.email)
        assertTrue(body.data!!.token.isNotBlank())
    }

    @Test
    fun `login fails with wrong password`() = testApplication {
        application { module() }
        val client = createJsonClient()

        // Register
        client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "wrong@example.com", password = "correct", name = "User"))
        }

        // Login with wrong password
        val response = client.post("/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "wrong@example.com", password = "incorrect"))
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
        val body = response.body<ApiResponse<AuthResponse>>()
        assertFalse(body.success)
    }

    @Test
    fun `login fails with nonexistent email`() = testApplication {
        application { module() }
        val client = createJsonClient()

        val response = client.post("/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email = "nobody@example.com", password = "pass"))
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}
