plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"

repositories {
    mavenCentral()
}

val grpcKotlinVersion: String by project
val ioGrpcProtoVersion: String by project
val comGoogleProtoVersion: String by project
dependencies {
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-protobuf:$ioGrpcProtoVersion")
    implementation("io.grpc:grpc-netty-shaded:$ioGrpcProtoVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$comGoogleProtoVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}