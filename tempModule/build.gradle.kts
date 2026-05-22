val ktor_version = "2.3.12"

plugins {
    kotlin("jvm")
}

group = "ru.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.kotest:kotest-framework-engine:5.9.1")
    implementation("io.arrow-kt:arrow-core:1.2.4")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}