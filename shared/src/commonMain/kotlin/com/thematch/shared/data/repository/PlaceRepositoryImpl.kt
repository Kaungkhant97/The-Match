package com.thematch.shared.data.repository

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.domain.repository.PlaceRepository
import com.thematch.shared.model.Place

class PlaceRepositoryImpl(private val api: TheMatchApi) : PlaceRepository {

    override suspend fun getPlaces(): Result<List<Place>> = runCatching {
        val response = api.getPlaces()
        if (response.success) response.data ?: emptyList()
        else throw Exception(response.message ?: "Failed to load places")
    }
}
