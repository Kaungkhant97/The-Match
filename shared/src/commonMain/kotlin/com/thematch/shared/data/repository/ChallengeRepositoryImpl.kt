package com.thematch.shared.data.repository

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.domain.repository.ChallengeRepository
import com.thematch.shared.model.Challenge
import com.thematch.shared.model.CreateChallengeRequest
import com.thematch.shared.model.Team

class ChallengeRepositoryImpl(private val api: TheMatchApi) : ChallengeRepository {

    override suspend fun getChallengableTeams(): Result<List<Team>> = runCatching {
        val response = api.getChallengableTeams()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load teams")
    }

    override suspend fun sendChallenge(
        challengerTeamId: Int,
        acceptedTeamId: Int,
        placeId: Int,
        reservedTime: String
    ): Result<Challenge> = runCatching {
        val response = api.sendChallenge(
            CreateChallengeRequest(challengerTeamId, acceptedTeamId, placeId, reservedTime)
        )
        if (response.success && response.data != null) response.data
        else throw Exception(response.message ?: "Failed to send challenge")
    }

    override suspend fun getAcceptedChallenges(): Result<List<Challenge>> = runCatching {
        val response = api.getAcceptedChallenges()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load challenges")
    }

    override suspend fun getPendingChallenges(): Result<List<Challenge>> = runCatching {
        val response = api.getPendingChallenges()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load challenges")
    }

    override suspend fun getChallengeHistory(): Result<List<Challenge>> = runCatching {
        val response = api.getChallengeHistory()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load challenges")
    }

    override suspend fun acceptChallenge(id: Int): Result<Challenge> = runCatching {
        val response = api.acceptChallenge(id)
        if (response.success && response.data != null) response.data
        else throw Exception(response.message ?: "Failed to accept challenge")
    }

    override suspend fun declineChallenge(id: Int): Result<Challenge> = runCatching {
        val response = api.declineChallenge(id)
        if (response.success && response.data != null) response.data
        else throw Exception(response.message ?: "Failed to decline challenge")
    }
}
