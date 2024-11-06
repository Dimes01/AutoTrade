package org.example

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
object PostgresContainer {
    private lateinit var postgresContainer: PostgreSQLContainer<*>

    @Value("\${spring.datasource.name}")
    private lateinit var dataBaseName: String

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String

    @JvmStatic
    @BeforeAll
    fun startContainer() {
        postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
            .withDatabaseName(dataBaseName)
            .withUsername(username)
            .withPassword(password)
            .withInitScript("db.sql")
        postgresContainer.start()
    }

    @JvmStatic
    @AfterAll
    fun stopContainer() {
        postgresContainer.stop()
    }

    @DynamicPropertySource
    private fun properties(registry: DynamicPropertyRegistry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
    }
}