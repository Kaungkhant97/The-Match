package com.thematch.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int,
    val name: String,
    val address: String,
    val region: String,
    val latitude: Double,
    val longitude: Double
)
