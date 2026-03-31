package com.thematch.server.data.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users : IntIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val name = varchar("name", 100)
    val profilePic = varchar("profile_pic", 500).nullable()
    val createdAt = datetime("created_at").default(LocalDateTime.now())
}

object Teams : IntIdTable("teams") {
    val name = varchar("name", 100)
    val logoUrl = varchar("logo_url", 500).nullable()
    val leaderId = reference("leader_id", Users)
    val point = integer("point").default(0)
    val status = varchar("status", 20).default("active")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
}

object TeamPlayers : IntIdTable("team_players") {
    val teamId = reference("team_id", Teams)
    val playerId = reference("player_id", Users)

    init {
        uniqueIndex(teamId, playerId)
    }
}

object Challenges : IntIdTable("challenges") {
    val challengerTeamId = reference("challenger_team_id", Teams)
    val acceptedTeamId = reference("accepted_team_id", Teams)
    val placeId = reference("place_id", Places).nullable()
    val reservedTime = datetime("reserved_time").nullable()
    val status = varchar("status", 20).default("pending")
    val point = integer("point").default(10)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
}

object Places : IntIdTable("places") {
    val name = varchar("name", 200)
    val address = varchar("address", 500)
    val region = varchar("region", 100)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}
