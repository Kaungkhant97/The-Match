package com.thematch.server.data.dao

import com.thematch.server.data.table.Places
import com.thematch.shared.model.Place
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object PlaceDao {

    fun findAll(): List<Place> = transaction {
        Places.selectAll().map {
            Place(
                id = it[Places.id].value,
                name = it[Places.name],
                address = it[Places.address],
                region = it[Places.region],
                latitude = it[Places.latitude],
                longitude = it[Places.longitude]
            )
        }
    }
}
