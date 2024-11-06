package org.example.dto

import jakarta.validation.constraints.NotBlank

data class SignUpResponse(
    val id: Long,
    val name: String
)