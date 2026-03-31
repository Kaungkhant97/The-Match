package com.thematch.shared.domain.repository

import com.thematch.shared.model.AuthResponse
import com.thematch.shared.model.User

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String): Result<AuthResponse>
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun clearToken()
    suspend fun getCurrentUser(): User?
    suspend fun saveCurrentUser(user: User)
    fun isLoggedIn(): Boolean
}
