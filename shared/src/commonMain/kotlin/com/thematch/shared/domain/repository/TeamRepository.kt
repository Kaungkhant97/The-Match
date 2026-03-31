package com.thematch.shared.domain.repository

import com.thematch.shared.model.*

interface TeamRepository {
    suspend fun getTeams(): Result<List<Team>>
    suspend fun getTeamDetail(id: Int): Result<TeamDetail>
    suspend fun createTeam(name: String, playerIds: List<Int>): Result<Team>
    suspend fun getTeamPlayers(teamId: Int): Result<List<Player>>
    suspend fun getAvailablePlayers(): Result<List<Player>>
    suspend fun getMyTeams(): Result<List<Team>>
}
