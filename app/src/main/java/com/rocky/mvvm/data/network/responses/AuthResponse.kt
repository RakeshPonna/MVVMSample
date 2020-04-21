package com.rocky.mvvm.data.network.responses

import com.rocky.mvvm.data.db.entities.User

data class AuthResponse(
    val isSuccess: Boolean?,
    val message: String?,
    val user: User?
)