package com.thematch.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val score: Int = 0,
    val profilePic: String? = null
)
