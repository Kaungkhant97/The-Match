package com.thematch.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Challenge(
    val id: Int,
    val challengerTeam: Team,
    val acceptedTeam: Team? = null,
    val place: Place? = null,
    val reservedTime: String? = null,
    val status: String = "pending",
    val point: Int = 10
)

@Serializable
data class CreateChallengeRequest(
    val challengerTeamId: Int,
    val acceptedTeamId: Int,
    val placeId: Int,
    val reservedTime: String
)
