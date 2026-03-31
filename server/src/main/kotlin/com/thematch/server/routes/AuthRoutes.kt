package com.thematch.server.routes

import com.thematch.server.data.dao.UserDao
import com.thematch.server.util.JwtConfig
import com.thematch.server.util.PasswordHash
import com.thematch.shared.model.ApiResponse
import com.thematch.shared.model.AuthRequest
import com.thematch.shared.model.AuthResponse
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    route("/api/auth") {
        post("/register") {
            val request = call.receive<AuthRequest>()

            if (request.email.isBlank() || request.password.isBlank() || request.name.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Nothing>(success = false, message = "Email, password, and name are required")
                )
                return@post
            }

            val existing = UserDao.findByEmail(request.email)
            if (existing != null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    ApiResponse<Nothing>(success = false, message = "Email already registered")
                )
                return@post
            }

            val hash = PasswordHash.hash(request.password)
            val user = UserDao.create(request.email, hash, request.name!!)
            val token = JwtConfig.makeToken(user.id, user.email)

            call.respond(
                HttpStatusCode.Created,
                ApiResponse(success = true, data = AuthResponse(token, user), message = "Registration successful")
            )
        }

        post("/login") {
            val request = call.receive<AuthRequest>()

            val result = UserDao.findByEmail(request.email)
            if (result == null || !PasswordHash.verify(request.password, result.second)) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ApiResponse<Nothing>(success = false, message = "Invalid email or password")
                )
                return@post
            }

            val (user, _) = result
            val token = JwtConfig.makeToken(user.id, user.email)

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(success = true, data = AuthResponse(token, user), message = "Login successful")
            )
        }
    }
}
