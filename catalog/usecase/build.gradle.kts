project.base.archivesName.set("workout-usecase")
plugins {
    kotlin("jvm")
}

group = "ru.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:2.2.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:2.2.1")
    implementation(project(":catalog:domain"))
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation(project(":catalog:domain"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}