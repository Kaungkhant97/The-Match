package com.thematch.shared.model

import kotlinx.serialization.json.Json
import kotlin.test.*

class ModelSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `User serializes and deserializes`() {
        val user = User(id = 1, email = "test@test.com", name = "Test User", profilePic = null)
        val jsonStr = json.encodeToString(User.serializer(), user)
        val decoded = json.decodeFromString(User.serializer(), jsonStr)
        assertEquals(user, decoded)
    }

    @Test
    fun `User deserializes with optional fields`() {
        val jsonStr = """{"id":1,"email":"a@b.com","name":"Test"}"""
        val user = json.decodeFromString(User.serializer(), jsonStr)
        assertEquals(1, user.id)
        assertEquals("a@b.com", user.email)
        assertNull(user.profilePic)
    }

    @Test
    fun `Team serializes and deserializes`() {
        val team = Team(id = 1, name = "FC Test", point = 100, status = "active")
        val jsonStr = json.encodeToString(Team.serializer(), team)
        val decoded = json.decodeFromString(Team.serializer(), jsonStr)
        assertEquals(team, decoded)
    }

    @Test
    fun `Team has correct defaults`() {
        val team = Team(id = 1, name = "Test")
        assertEquals(0, team.point)
        assertEquals("active", team.status)
        assertNull(team.logoUrl)
        assertNull(team.leader)
    }

    @Test
    fun `Player serializes and deserializes`() {
        val player = Player(id = 1, name = "John", score = 85, profilePic = "http://img.com/1.jpg")
        val jsonStr = json.encodeToString(Player.serializer(), player)
        val decoded = json.decodeFromString(Player.serializer(), jsonStr)
        assertEquals(player, decoded)
    }

    @Test
    fun `Player has correct defaults`() {
        val player = Player(id = 1, name = "Test")
        assertEquals(0, player.score)
        assertNull(player.profilePic)
    }

    @Test
    fun `Place serializes and deserializes`() {
        val place = Place(id = 1, name = "Stadium", address = "123 Main", region = "Downtown", latitude = 16.87, longitude = 96.19)
        val jsonStr = json.encodeToString(Place.serializer(), place)
        val decoded = json.decodeFromString(Place.serializer(), jsonStr)
        assertEquals(place, decoded)
    }

    @Test
    fun `Challenge serializes and deserializes`() {
        val challenge = Challenge(
            id = 1,
            challengerTeam = Team(id = 1, name = "Team A"),
            acceptedTeam = Team(id = 2, name = "Team B"),
            place = Place(id = 1, name = "Arena", address = "Addr", region = "Region", latitude = 0.0, longitude = 0.0),
            reservedTime = "2026-04-15T14:00:00",
            status = "pending",
            point = 10
        )
        val jsonStr = json.encodeToString(Challenge.serializer(), challenge)
        val decoded = json.decodeFromString(Challenge.serializer(), jsonStr)
        assertEquals(challenge, decoded)
    }

    @Test
    fun `Challenge has correct defaults`() {
        val challenge = Challenge(id = 1, challengerTeam = Team(id = 1, name = "A"))
        assertEquals("pending", challenge.status)
        assertEquals(10, challenge.point)
        assertNull(challenge.acceptedTeam)
        assertNull(challenge.place)
        assertNull(challenge.reservedTime)
    }

    @Test
    fun `TeamDetail serializes with players`() {
        val detail = TeamDetail(
            id = 1,
            name = "Full Team",
            players = listOf(
                Player(id = 1, name = "Player 1"),
                Player(id = 2, name = "Player 2", score = 50)
            )
        )
        val jsonStr = json.encodeToString(TeamDetail.serializer(), detail)
        val decoded = json.decodeFromString(TeamDetail.serializer(), jsonStr)
        assertEquals(2, decoded.players.size)
        assertEquals("Player 1", decoded.players[0].name)
    }

    @Test
    fun `AuthRequest serializes correctly`() {
        val request = AuthRequest(email = "a@b.com", password = "pass123", name = "Test")
        val jsonStr = json.encodeToString(AuthRequest.serializer(), request)
        assertTrue(jsonStr.contains("\"email\":\"a@b.com\""))
        assertTrue(jsonStr.contains("\"password\":\"pass123\""))
    }

    @Test
    fun `AuthRequest name is optional`() {
        val request = AuthRequest(email = "a@b.com", password = "pass")
        val jsonStr = json.encodeToString(AuthRequest.serializer(), request)
        val decoded = json.decodeFromString(AuthRequest.serializer(), jsonStr)
        assertNull(decoded.name)
    }

    @Test
    fun `AuthResponse serializes correctly`() {
        val response = AuthResponse(
            token = "jwt-token-here",
            user = User(id = 1, email = "a@b.com", name = "Test")
        )
        val jsonStr = json.encodeToString(AuthResponse.serializer(), response)
        val decoded = json.decodeFromString(AuthResponse.serializer(), jsonStr)
        assertEquals("jwt-token-here", decoded.token)
        assertEquals(1, decoded.user.id)
    }

    @Test
    fun `CreateTeamRequest serializes correctly`() {
        val request = CreateTeamRequest(name = "New Team", playerIds = listOf(1, 2, 3))
        val jsonStr = json.encodeToString(CreateTeamRequest.serializer(), request)
        val decoded = json.decodeFromString(CreateTeamRequest.serializer(), jsonStr)
        assertEquals("New Team", decoded.name)
        assertEquals(listOf(1, 2, 3), decoded.playerIds)
    }

    @Test
    fun `CreateChallengeRequest serializes correctly`() {
        val request = CreateChallengeRequest(
            challengerTeamId = 1,
            acceptedTeamId = 2,
            placeId = 3,
            reservedTime = "2026-04-15T14:00:00"
        )
        val jsonStr = json.encodeToString(CreateChallengeRequest.serializer(), request)
        val decoded = json.decodeFromString(CreateChallengeRequest.serializer(), jsonStr)
        assertEquals(1, decoded.challengerTeamId)
        assertEquals(2, decoded.acceptedTeamId)
        assertEquals(3, decoded.placeId)
    }

    @Test
    fun `ApiResponse wraps data correctly`() {
        val response = ApiResponse(success = true, data = "hello", message = "ok")
        val jsonStr = json.encodeToString(ApiResponse.serializer(kotlinx.serialization.builtins.serializer<String>()), response)
        assertTrue(jsonStr.contains("\"success\":true"))
        assertTrue(jsonStr.contains("\"data\":\"hello\""))
    }

    @Test
    fun `ApiResponse error has null data`() {
        val response = ApiResponse<String>(success = false, data = null, message = "Error occurred")
        val jsonStr = json.encodeToString(ApiResponse.serializer(kotlinx.serialization.builtins.serializer<String>()), response)
        val decoded = json.decodeFromString(ApiResponse.serializer(kotlinx.serialization.builtins.serializer<String>()), jsonStr)
        assertFalse(decoded.success)
        assertNull(decoded.data)
        assertEquals("Error occurred", decoded.message)
    }
}
