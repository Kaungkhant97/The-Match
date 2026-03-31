package com.thematch.shared.domain.repository

import com.thematch.shared.model.Place

interface PlaceRepository {
    suspend fun getPlaces(): Result<List<Place>>
}
