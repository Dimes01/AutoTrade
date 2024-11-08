package org.example.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.AutoTradeAuthApplication
import org.example.dto.SignUpRequest
import org.example.entities.User
import org.example.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@SpringBootTest(classes = [AutoTradeAuthApplication::class])
@AutoConfigureMockMvc
class AuthControllerIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val userRepository: UserRepository,
) {
    private val utilMapper: ObjectMapper = ObjectMapper()
    private val signUpRequest: SignUpRequest = SignUpRequest("user1", "user1@mail.ru", "1111", "Ivan")

    private companion object {
        @JvmStatic
        fun validationSignUpParameters(): Stream<Arguments> = Stream.of(
            Arguments.of(SignUpRequest("test", "test@mail.ru", "0", "Test"), status().isOk),
            Arguments.of(SignUpRequest("test", "test@mail.ru", "0", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "test@mail.ru", "", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "test@mail.ru", "", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "", "0", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "", "0", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "", "", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("test", "", "", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("", "test@mail.ru", "0", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("", "test@mail.ru", "0", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("", "test@mail.ru", "", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("", "test@mail.ru", "", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("", "", "0", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("", "", "0", ""), status().isBadRequest),
            Arguments.of(SignUpRequest("", "", "", "Test"), status().isBadRequest),
            Arguments.of(SignUpRequest("", "", "", ""), status().isBadRequest),
        )
    }

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun register_notExistedUser_ok() {
        // Arrange
        val requestString = utilMapper.writeValueAsString(signUpRequest)

        // Act
        val responseString = mockMvc.perform(
            post("/register")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
        val user = utilMapper.readValue(responseString, User::class.java)
        val expectedUser = userRepository.findByUsername(signUpRequest.username)

        // Arrange
        assertEquals(expectedUser, user)
    }

    @Test
    fun register_existedUser_badRequest() {
        // Arrange
        val requestString = utilMapper.writeValueAsString(signUpRequest)
        val user = User(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = signUpRequest.password,
            name = signUpRequest.name
        )
        userRepository.save(user)

        // Act
        mockMvc.perform(
            post("/register")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

        // Assert
        assertNotNull(userRepository.findByUsername(signUpRequest.username))
    }

    @ParameterizedTest
    @MethodSource("validationSignUpParameters")
    fun register_validateParams_allSituations(testSignUpRequest: SignUpRequest, resultMatcher: ResultMatcher) {
        // Arrange
        val requestString = utilMapper.writeValueAsString(testSignUpRequest)

        // Act & Assert
        mockMvc.perform(
            post("/register")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(resultMatcher)
    }
}