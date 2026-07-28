val ktor_version = "2.3.12"
val kotlin_version = "2.0.0"
val logback_version = "1.4.14"
val kotest_version = "5.9.1" // Добавили версию здесь

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    kotlin("plugin.serialization") version "2.0.0"
    id("java-test-fixtures")
}

group = "ru.workout"
version = "0.0.1"

application {
    mainClass.set("ru.workout.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.kotest:kotest-framework-engine:5.9.1")
//    implementation("io.arrow-kt:arrow-core:1.2.4")
    implementation(project(":training:usecase"))
    implementation(project(":training:domain"))
//    implementation("io.ktor:ktor-server-static-jvm")

    // ТЕСТОВЫЕ ОСНОВНЫЕ
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktor_version")
    testImplementation("org.jsoup:jsoup:1.17.2")

    // KOTEST (Теперь с версиями!)
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.kotest:kotest-framework-engine:$kotest_version")

    testImplementation("io.ktor:ktor-client-core-jvm:${ktor_version}")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:${ktor_version}")
    testImplementation("io.ktor:ktor-client-resources-jvm:${ktor_version}")
// Если используешь JSON в тестах:
    testImplementation("io.ktor:ktor-serialization-kotlinx-json-jvm:${ktor_version}")

    implementation("io.arrow-kt:arrow-core:2.2.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:2.2.1")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")

    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}

ktor {
    fatJar {
        archiveFileName.set("app-all.jar")
    }
}