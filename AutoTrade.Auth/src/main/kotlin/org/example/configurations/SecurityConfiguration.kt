package org.example.configurations

import jakarta.servlet.http.HttpSession
import org.example.services.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration(
    private val userDetailsService: CustomUserDetailsService,

    @Value("\${server.servlet.session.timeout}")
    private var sessionTimeout: Int
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { it
                .requestMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                it.sessionFixation().migrateSession()
            }
            .formLogin{ it.disable() }
            .httpBasic {  }

        return http.build()
    }

    @Bean
    fun httpSessionCustomizer(): (HttpSession) -> Unit = { it.maxInactiveInterval = sessionTimeout }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return userDetailsService
    }
}