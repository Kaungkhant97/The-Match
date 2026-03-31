package com.thematch.server.plugins

import com.thematch.server.routes.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(CallLogging)

    routing {
        authRoutes()
        teamRoutes()
        challengeRoutes()
        placeRoutes()
    }
}
