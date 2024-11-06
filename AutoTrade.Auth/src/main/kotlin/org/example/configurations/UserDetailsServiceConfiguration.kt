package org.example.configurations

import org.example.services.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class UserDetailsServiceConfiguration(
    private val userDetailsService: CustomUserDetailsService
) {
    @Bean
    fun userDetailsService(): UserDetailsService {
        return userDetailsService
    }
}