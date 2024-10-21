import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.9.4"
}

group = "org.example"

repositories {
    mavenCentral()
}

val grpcKotlinVersion: String by project
val ioGrpcProtoVersion: String by project
val comGoogleProtoVersion: String by project
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$comGoogleProtoVersion"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$ioGrpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("src/main/contracts")
        }
    }
}

kotlin {
    jvmToolchain(21)
}