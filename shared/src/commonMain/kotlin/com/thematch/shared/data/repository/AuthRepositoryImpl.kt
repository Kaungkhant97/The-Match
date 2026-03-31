package com.thematch.shared.data.repository

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.domain.repository.AuthRepository
import com.thematch.shared.model.AuthRequest
import com.thematch.shared.model.AuthResponse
import com.thematch.shared.model.User

class AuthRepositoryImpl(
    private val api: TheMatchApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    private var cachedUser: User? = null

    override suspend fun register(email: String, password: String, name: String): Result<AuthResponse> =
        runCatching {
            val response = api.register(AuthRequest(email, password, name))
            if (response.success && response.data != null) {
                saveToken(response.data.token)
                saveCurrentUser(response.data.user)
                response.data
            } else {
                throw Exception(response.message ?: "Registration failed")
            }
        }

    override suspend fun login(email: String, password: String): Result<AuthResponse> =
        runCatching {
            val response = api.login(AuthRequest(email, password))
            if (response.success && response.data != null) {
                saveToken(response.data.token)
                saveCurrentUser(response.data.user)
                response.data
            } else {
                throw Exception(response.message ?: "Login failed")
            }
        }

    override suspend fun getToken(): String? = tokenStorage.getToken()

    override suspend fun saveToken(token: String) = tokenStorage.saveToken(token)

    override suspend fun clearToken() {
        tokenStorage.clearToken()
        cachedUser = null
    }

    override suspend fun getCurrentUser(): User? = cachedUser

    override suspend fun saveCurrentUser(user: User) {
        cachedUser = user
    }

    override fun isLoggedIn(): Boolean = tokenStorage.hasToken()
}

interface TokenStorage {
    fun getToken(): String?
    fun saveToken(token: String)
    fun clearToken()
    fun hasToken(): Boolean
}

class InMemoryTokenStorage : TokenStorage {
    private var token: String? = null

    override fun getToken(): String? = token
    override fun saveToken(token: String) { this.token = token }
    override fun clearToken() { token = null }
    override fun hasToken(): Boolean = token != null
}
