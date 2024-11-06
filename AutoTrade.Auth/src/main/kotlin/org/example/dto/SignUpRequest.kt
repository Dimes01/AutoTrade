package org.example.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpRequest(
    @field:NotBlank(message = "Username cannot be blank")
    val username: String,

    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String
)
