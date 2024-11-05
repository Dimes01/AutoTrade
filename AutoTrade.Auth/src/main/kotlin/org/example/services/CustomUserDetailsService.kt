package org.example.services

import jakarta.transaction.Transactional
import org.example.entities.User
import org.example.repositories.UserRepository
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return SpringUser(user.name, user.password, emptyList())
    }

    @Transactional
    fun registerUser(name: String, email: String, password: String): User {
        if (userRepository.findByUsername(name) != null) {
            throw IllegalArgumentException("User already exists")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(name = name, email = email, password = encodedPassword)
        return userRepository.save(user)
    }
}