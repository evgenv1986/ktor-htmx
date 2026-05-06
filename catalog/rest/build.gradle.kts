plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

group = "ru.workout"
version = "0.0.1"

val ktorVersion = "2.3.12"
val kotlinVersion = "2.0.0"
val logbackVersion = "1.4.14"
val kotestVersion = "5.9.1"
val arrowVersion = "1.2.4"
val kotestArrowVersion = "1.4.0"

base {
    archivesName.set("workout-rest")
}

// Если нужно запускать приложение через Gradle, раскомментируй и проверь package
// application {
//     mainClass.set("ru.workout.ApplicationKt")
// }

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catalog:domain"))
    implementation(project(":catalog:usecase"))

    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.kotest:kotest-framework-engine:5.9.1")

    // Tests
    testImplementation(kotlin("test"))

    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-resources-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:$kotestArrowVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}