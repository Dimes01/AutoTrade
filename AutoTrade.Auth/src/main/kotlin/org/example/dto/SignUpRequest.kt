package org.example.dto

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
)