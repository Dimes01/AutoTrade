package org.example.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.example.dto.LoginRequest
import org.example.dto.SignUpRequest
import org.example.entities.User
import org.example.services.CustomUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<User> {
        return try {
            val user = userDetailsService.registerUser(
                signUpRequest.username,
                signUpRequest.email,
                signUpRequest.password,
                signUpRequest.name)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest, session: HttpSession): ResponseEntity<String> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        session.setAttribute("user", authentication.principal as UserDetails)
        return ResponseEntity.ok("User authenticated successfully")
    }

    @GetMapping("/check-session")
    fun checkSession(request: HttpServletRequest): ResponseEntity<String> {
        val session = request.session
        return if (session.isNew) {
            ResponseEntity.ok("Session is not valid.")
        } else {
            ResponseEntity.status(401).body("Session is valid. Last accessed time: ${session.lastAccessedTime}")
        }
    }
}