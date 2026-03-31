package com.thematch.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val logoUrl: String? = null,
    val leader: User? = null,
    val point: Int = 0,
    val status: String = "active"
)

@Serializable
data class TeamDetail(
    val id: Int,
    val name: String,
    val logoUrl: String? = null,
    val leader: User? = null,
    val point: Int = 0,
    val players: List<Player> = emptyList()
)

@Serializable
data class CreateTeamRequest(
    val name: String,
    val playerIds: List<Int>
)
