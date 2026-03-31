package com.thematch.server.routes

import com.thematch.server.data.dao.ChallengeDao
import com.thematch.server.data.dao.TeamDao
import com.thematch.server.plugins.userId
import com.thematch.shared.model.ApiResponse
import com.thematch.shared.model.CreateChallengeRequest
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.challengeRoutes() {
    route("/api") {
        authenticate("auth-jwt") {
            get("/challenges") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val myTeamIds = TeamDao.findByUserId(userId).map { it.id }
                val allTeams = TeamDao.findAll().filter { it.id !in myTeamIds }
                call.respond(ApiResponse(success = true, data = allTeams))
            }

            post("/challenges") {
                val request = call.receive<CreateChallengeRequest>()
                val challenge = ChallengeDao.create(
                    challengerTeamId = request.challengerTeamId,
                    acceptedTeamId = request.acceptedTeamId,
                    placeId = request.placeId,
                    reservedTime = request.reservedTime
                )
                call.respond(
                    HttpStatusCode.Created,
                    ApiResponse(success = true, data = challenge, message = "Challenge sent successfully")
                )
            }

            get("/challenges/accepted") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val teamIds = TeamDao.findByUserId(userId).map { it.id }
                val challenges = ChallengeDao.findByTeamAndStatus(teamIds, "accepted")
                call.respond(ApiResponse(success = true, data = challenges))
            }

            get("/challenges/pending") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val teamIds = TeamDao.findByUserId(userId).map { it.id }
                val challenges = ChallengeDao.findByTeamAndStatus(teamIds, "pending")
                call.respond(ApiResponse(success = true, data = challenges))
            }

            get("/challenges/history") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = principal.userId()
                val teamIds = TeamDao.findByUserId(userId).map { it.id }
                val challenges = ChallengeDao.findHistoryByTeam(teamIds)
                call.respond(ApiResponse(success = true, data = challenges))
            }

            post("/challenges/{id}/accept") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse<Nothing>(success = false, message = "Invalid challenge ID"))
                    return@post
                }

                val challenge = ChallengeDao.updateStatus(id, "accepted")
                if (challenge == null) {
                    call.respond(HttpStatusCode.NotFound, ApiResponse<Nothing>(success = false, message = "Challenge not found"))
                    return@post
                }

                call.respond(ApiResponse(success = true, data = challenge, message = "Challenge accepted"))
            }

            post("/challenges/{id}/decline") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse<Nothing>(success = false, message = "Invalid challenge ID"))
                    return@post
                }

                val challenge = ChallengeDao.updateStatus(id, "declined")
                if (challenge == null) {
                    call.respond(HttpStatusCode.NotFound, ApiResponse<Nothing>(success = false, message = "Challenge not found"))
                    return@post
                }

                call.respond(ApiResponse(success = true, data = challenge, message = "Challenge declined"))
            }
        }
    }
}
