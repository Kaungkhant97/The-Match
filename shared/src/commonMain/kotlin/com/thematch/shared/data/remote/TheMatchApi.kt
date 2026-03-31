package com.thematch.shared.data.remote

import com.thematch.shared.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class TheMatchApi(private val client: HttpClient, private val baseUrl: String) {

    // Auth
    suspend fun register(request: AuthRequest): ApiResponse<AuthResponse> =
        client.post("$baseUrl/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun login(request: AuthRequest): ApiResponse<AuthResponse> =
        client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    // Teams
    suspend fun getTeams(): ApiResponse<List<Team>> =
        client.get("$baseUrl/teams").body()

    suspend fun getTeamDetail(id: Int): ApiResponse<TeamDetail> =
        client.get("$baseUrl/teams/$id").body()

    suspend fun createTeam(request: CreateTeamRequest): ApiResponse<Team> =
        client.post("$baseUrl/teams") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun getTeamPlayers(teamId: Int): ApiResponse<List<Player>> =
        client.get("$baseUrl/teams/$teamId/players").body()

    suspend fun getPlayers(): ApiResponse<List<Player>> =
        client.get("$baseUrl/players").body()

    suspend fun getMyTeams(): ApiResponse<List<Team>> =
        client.get("$baseUrl/me/teams").body()

    // Challenges
    suspend fun getChallengableTeams(): ApiResponse<List<Team>> =
        client.get("$baseUrl/challenges").body()

    suspend fun sendChallenge(request: CreateChallengeRequest): ApiResponse<Challenge> =
        client.post("$baseUrl/challenges") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun getAcceptedChallenges(): ApiResponse<List<Challenge>> =
        client.get("$baseUrl/challenges/accepted").body()

    suspend fun getPendingChallenges(): ApiResponse<List<Challenge>> =
        client.get("$baseUrl/challenges/pending").body()

    suspend fun getChallengeHistory(): ApiResponse<List<Challenge>> =
        client.get("$baseUrl/challenges/history").body()

    suspend fun acceptChallenge(id: Int): ApiResponse<Challenge> =
        client.post("$baseUrl/challenges/$id/accept").body()

    suspend fun declineChallenge(id: Int): ApiResponse<Challenge> =
        client.post("$baseUrl/challenges/$id/decline").body()

    // Places
    suspend fun getPlaces(): ApiResponse<List<Place>> =
        client.get("$baseUrl/places").body()
}
