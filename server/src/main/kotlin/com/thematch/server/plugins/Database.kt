package com.thematch.server.plugins

import com.thematch.server.data.table.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:h2:mem:thematch;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )

    transaction {
        SchemaUtils.create(Users, Teams, TeamPlayers, Challenges, Places)
        seedPlaces()
    }
}

private fun seedPlaces() {
    if (Places.select(Places.id).count() == 0L) {
        val placesData = listOf(
            PlaceSeed("Central Stadium", "123 Main Street", "Downtown", 16.8661, 96.1951),
            PlaceSeed("Riverside Arena", "456 River Road", "Riverside", 16.8500, 96.1800),
            PlaceSeed("North Park Field", "789 Park Avenue", "Northside", 16.8800, 96.2000),
            PlaceSeed("South Sports Complex", "321 Sports Drive", "Southside", 16.8400, 96.1700),
            PlaceSeed("University Ground", "654 Campus Road", "University", 16.8550, 96.1900),
        )
        for (place in placesData) {
            Places.insertAndGetId {
                it[name] = place.name
                it[address] = place.address
                it[region] = place.region
                it[latitude] = place.latitude
                it[longitude] = place.longitude
            }
        }
    }
}

private data class PlaceSeed(
    val name: String,
    val address: String,
    val region: String,
    val latitude: Double,
    val longitude: Double
)
