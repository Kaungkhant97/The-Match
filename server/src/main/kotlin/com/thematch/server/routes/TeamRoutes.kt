package com.thematch.server.routes

import com.thematch.server.data.dao.TeamDao
import com.thematch.server.data.dao.UserDao
import com.thematch.server.plugins.userId
import com.thematch.shared.model.ApiResponse
import com.thematch.shared.model.CreateTeamRequest
import com.thematch.shared.model.Player
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.teamRoutes() {
    route("/api") {
        authenticate("auth-jwt") {
            get("/teams") {
                val teams = TeamDao.findAll()
                call.respond(ApiResponse(success = true, data = teams))
            }

            get("/teams/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse<Nothing>(success = false, message = "Invalid team ID"))
                    return@get
                }

                val team = TeamDao.findById(id)
                if (team == null) {
                    call.respond(HttpStatusCode.NotFound, ApiResponse<Nothing>(success = false, message = "Team not found"))
                    return@get
                }

                call.respond(ApiResponse(success = true, data = team))
            }

            post("/teams") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val request = call.receive<CreateTeamRequest>()

                if (request.name.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse<Nothing>(success = false, message = "Team name is required"))
                    return@post
                }

                val team = TeamDao.create(request.name, userId, request.playerIds)
                call.respond(HttpStatusCode.Created, ApiResponse(success = true, data = team, message = "Team created successfully"))
            }

            get("/teams/{id}/players") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse<Nothing>(success = false, message = "Invalid team ID"))
                    return@get
                }

                val players = TeamDao.findPlayersForTeam(id)
                call.respond(ApiResponse(success = true, data = players))
            }

            get("/players") {
                val users = UserDao.findAll()
                val players = users.map { Player(id = it.id, name = it.name, score = 0, profilePic = it.profilePic) }
                call.respond(ApiResponse(success = true, data = players))
            }

            get("/me/teams") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val teams = TeamDao.findByUserId(userId)
                call.respond(ApiResponse(success = true, data = teams))
            }
        }
    }
}
