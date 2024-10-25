plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ru.tinkoff.piapi:java-sdk-core:1.24")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}