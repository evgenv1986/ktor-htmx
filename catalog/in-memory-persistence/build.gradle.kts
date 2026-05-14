project.base.archivesName.set("workout-catalog-in-memory-persistence")

plugins {
    kotlin("jvm")
}

group = "ru.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

val kotestVersion = "5.9.1"

dependencies {
    implementation(project(":catalog:usecase"))
    implementation(project(":catalog:domain"))
    implementation(project(":common"))
    testImplementation(project(":common"))
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation(project(":catalog:domain"))
    testImplementation(testFixtures(project(":catalog:domain")))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}