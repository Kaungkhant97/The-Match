package com.thematch.server.data.dao

import com.thematch.server.data.table.Users
import com.thematch.shared.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserDao {

    fun findByEmail(email: String): Pair<User, String>? = transaction {
        Users.selectAll().where { Users.email eq email }
            .firstOrNull()?.let {
                Pair(it.toUser(), it[Users.passwordHash])
            }
    }

    fun findById(id: Int): User? = transaction {
        Users.selectAll().where { Users.id eq id }
            .firstOrNull()?.toUser()
    }

    fun create(email: String, passwordHash: String, name: String): User = transaction {
        val id = Users.insertAndGetId {
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
            it[Users.name] = name
        }
        User(id = id.value, email = email, name = name)
    }

    fun findAll(): List<User> = transaction {
        Users.selectAll().map { it.toUser() }
    }

    private fun ResultRow.toUser() = User(
        id = this[Users.id].value,
        email = this[Users.email],
        name = this[Users.name],
        profilePic = this[Users.profilePic]
    )
}
