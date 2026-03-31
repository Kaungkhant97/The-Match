package com.thematch.server

import com.thematch.server.plugins.*
import com.thematch.server.routes.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabase()
    configureSecurity()
    configureRouting()
}
