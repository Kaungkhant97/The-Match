package com.thematch.shared.domain.repository

import com.thematch.shared.model.Challenge
import com.thematch.shared.model.Team

interface ChallengeRepository {
    suspend fun getChallengableTeams(): Result<List<Team>>
    suspend fun sendChallenge(challengerTeamId: Int, acceptedTeamId: Int, placeId: Int, reservedTime: String): Result<Challenge>
    suspend fun getAcceptedChallenges(): Result<List<Challenge>>
    suspend fun getPendingChallenges(): Result<List<Challenge>>
    suspend fun getChallengeHistory(): Result<List<Challenge>>
    suspend fun acceptChallenge(id: Int): Result<Challenge>
    suspend fun declineChallenge(id: Int): Result<Challenge>
}
