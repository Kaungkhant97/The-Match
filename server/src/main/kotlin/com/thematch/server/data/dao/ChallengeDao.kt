package com.thematch.server.data.dao

import com.thematch.server.data.table.Challenges
import com.thematch.server.data.table.Places
import com.thematch.server.data.table.Teams
import com.thematch.shared.model.Challenge
import com.thematch.shared.model.Place
import com.thematch.shared.model.Team
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ChallengeDao {

    fun create(
        challengerTeamId: Int,
        acceptedTeamId: Int,
        placeId: Int,
        reservedTime: String
    ): Challenge = transaction {
        val parsedTime = LocalDateTime.parse(reservedTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val challengeId = Challenges.insertAndGetId {
            it[Challenges.challengerTeamId] = challengerTeamId
            it[Challenges.acceptedTeamId] = acceptedTeamId
            it[Challenges.placeId] = placeId
            it[Challenges.reservedTime] = parsedTime
        }
        findById(challengeId.value)!!
    }

    fun findById(id: Int): Challenge? = transaction {
        Challenges.selectAll().where { Challenges.id eq id }
            .firstOrNull()?.toChallenge()
    }

    fun findByTeamAndStatus(teamIds: List<Int>, status: String): List<Challenge> = transaction {
        if (teamIds.isEmpty()) return@transaction emptyList()
        Challenges.selectAll().where {
            ((Challenges.challengerTeamId inList teamIds) or (Challenges.acceptedTeamId inList teamIds)) and
                    (Challenges.status eq status)
        }.map { it.toChallenge() }
    }

    fun findHistoryByTeam(teamIds: List<Int>): List<Challenge> = transaction {
        if (teamIds.isEmpty()) return@transaction emptyList()
        Challenges.selectAll().where {
            ((Challenges.challengerTeamId inList teamIds) or (Challenges.acceptedTeamId inList teamIds)) and
                    (Challenges.status inList listOf("completed", "declined"))
        }.map { it.toChallenge() }
    }

    fun updateStatus(id: Int, status: String): Challenge? = transaction {
        Challenges.update({ Challenges.id eq id }) {
            it[Challenges.status] = status
        }
        findById(id)
    }

    private fun ResultRow.toChallenge(): Challenge {
        val challengerTeam = getTeamById(this[Challenges.challengerTeamId].value)
        val acceptedTeam = getTeamById(this[Challenges.acceptedTeamId].value)
        val placeId = this[Challenges.placeId]?.value
        val place = placeId?.let { getPlaceById(it) }

        return Challenge(
            id = this[Challenges.id].value,
            challengerTeam = challengerTeam,
            acceptedTeam = acceptedTeam,
            place = place,
            reservedTime = this[Challenges.reservedTime]?.toString(),
            status = this[Challenges.status],
            point = this[Challenges.point]
        )
    }

    private fun getTeamById(id: Int): Team {
        val row = Teams.selectAll().where { Teams.id eq id }.first()
        return Team(
            id = row[Teams.id].value,
            name = row[Teams.name],
            logoUrl = row[Teams.logoUrl],
            point = row[Teams.point],
            status = row[Teams.status]
        )
    }

    private fun getPlaceById(id: Int): Place {
        val row = Places.selectAll().where { Places.id eq id }.first()
        return Place(
            id = row[Places.id].value,
            name = row[Places.name],
            address = row[Places.address],
            region = row[Places.region],
            latitude = row[Places.latitude],
            longitude = row[Places.longitude]
        )
    }
}
