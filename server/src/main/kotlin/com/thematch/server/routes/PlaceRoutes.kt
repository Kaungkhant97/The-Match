package com.thematch.server.routes

import com.thematch.server.data.dao.PlaceDao
import com.thematch.shared.model.ApiResponse
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.placeRoutes() {
    route("/api") {
        authenticate("auth-jwt") {
            get("/places") {
                val places = PlaceDao.findAll()
                call.respond(ApiResponse(success = true, data = places))
            }
        }
    }
}
