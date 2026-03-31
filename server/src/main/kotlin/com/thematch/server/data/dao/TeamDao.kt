package com.thematch.server.data.dao

import com.thematch.server.data.table.TeamPlayers
import com.thematch.server.data.table.Teams
import com.thematch.server.data.table.Users
import com.thematch.shared.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object TeamDao {

    fun findAll(): List<Team> = transaction {
        (Teams innerJoin Users)
            .selectAll()
            .map { it.toTeam() }
    }

    fun findById(id: Int): TeamDetail? = transaction {
        val teamRow = (Teams innerJoin Users)
            .selectAll().where { Teams.id eq id }
            .firstOrNull() ?: return@transaction null

        val players = (TeamPlayers innerJoin Users)
            .selectAll().where { TeamPlayers.teamId eq id }
            .map {
                Player(
                    id = it[Users.id].value,
                    name = it[Users.name],
                    score = 0,
                    profilePic = it[Users.profilePic]
                )
            }

        TeamDetail(
            id = teamRow[Teams.id].value,
            name = teamRow[Teams.name],
            logoUrl = teamRow[Teams.logoUrl],
            leader = User(
                id = teamRow[Users.id].value,
                email = teamRow[Users.email],
                name = teamRow[Users.name],
                profilePic = teamRow[Users.profilePic]
            ),
            point = teamRow[Teams.point],
            players = players
        )
    }

    fun create(name: String, leaderId: Int, playerIds: List<Int>): Team = transaction {
        val teamId = Teams.insertAndGetId {
            it[Teams.name] = name
            it[Teams.leaderId] = leaderId
        }

        // Add leader as a player
        TeamPlayers.insert {
            it[TeamPlayers.teamId] = teamId
            it[playerId] = leaderId
        }

        // Add other players
        for (pid in playerIds) {
            TeamPlayers.insert {
                it[TeamPlayers.teamId] = teamId
                it[playerId] = pid
            }
        }

        val leader = UserDao.findById(leaderId)
        Team(
            id = teamId.value,
            name = name,
            leader = leader,
            point = 0,
            status = "active"
        )
    }

    fun findByUserId(userId: Int): List<Team> = transaction {
        val teamIds = TeamPlayers.selectAll()
            .where { TeamPlayers.playerId eq userId }
            .map { it[TeamPlayers.teamId].value }

        if (teamIds.isEmpty()) return@transaction emptyList()

        (Teams innerJoin Users)
            .selectAll().where { Teams.id inList teamIds }
            .map { it.toTeam() }
    }

    fun findPlayersForTeam(teamId: Int): List<Player> = transaction {
        (TeamPlayers innerJoin Users)
            .selectAll().where { TeamPlayers.teamId eq teamId }
            .map {
                Player(
                    id = it[Users.id].value,
                    name = it[Users.name],
                    score = 0,
                    profilePic = it[Users.profilePic]
                )
            }
    }

    private fun ResultRow.toTeam(): Team {
        return Team(
            id = this[Teams.id].value,
            name = this[Teams.name],
            logoUrl = this[Teams.logoUrl],
            leader = User(
                id = this[Users.id].value,
                email = this[Users.email],
                name = this[Users.name],
                profilePic = this[Users.profilePic]
            ),
            point = this[Teams.point],
            status = this[Teams.status]
        )
    }
}
