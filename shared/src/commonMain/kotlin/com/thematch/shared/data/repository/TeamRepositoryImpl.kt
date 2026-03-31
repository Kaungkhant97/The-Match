package com.thematch.shared.data.repository

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.domain.repository.TeamRepository
import com.thematch.shared.model.*

class TeamRepositoryImpl(private val api: TheMatchApi) : TeamRepository {

    override suspend fun getTeams(): Result<List<Team>> = runCatching {
        val response = api.getTeams()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load teams")
    }

    override suspend fun getTeamDetail(id: Int): Result<TeamDetail> = runCatching {
        val response = api.getTeamDetail(id)
        if (response.success && response.data != null) response.data
        else throw Exception(response.message ?: "Failed to load team detail")
    }

    override suspend fun createTeam(name: String, playerIds: List<Int>): Result<Team> = runCatching {
        val response = api.createTeam(CreateTeamRequest(name, playerIds))
        if (response.success && response.data != null) response.data
        else throw Exception(response.message ?: "Failed to create team")
    }

    override suspend fun getTeamPlayers(teamId: Int): Result<List<Player>> = runCatching {
        val response = api.getTeamPlayers(teamId)
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load players")
    }

    override suspend fun getAvailablePlayers(): Result<List<Player>> = runCatching {
        val response = api.getPlayers()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load players")
    }

    override suspend fun getMyTeams(): Result<List<Team>> = runCatching {
        val response = api.getMyTeams()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load teams")
    }
}
