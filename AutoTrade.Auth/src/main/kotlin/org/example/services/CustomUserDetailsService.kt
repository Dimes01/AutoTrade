package org.example.services

import jakarta.transaction.Transactional
import org.example.entities.User
import org.example.repositories.UserRepository
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return SpringUser(user.username, user.password, emptyList())
    }

    @Transactional
    fun registerUser(username: String, email: String, password: String, name: String): User {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("User already exists")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(username = username, email = email, password = encodedPassword, name = name)
        return userRepository.save(user)
    }
}