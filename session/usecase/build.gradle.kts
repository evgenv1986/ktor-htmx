plugins {
    kotlin("jvm")
    id("java-test-fixtures")
}

group = "ru.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:2.2.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:2.2.1")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
//    testImplementation(project(":application"))
//    implementation(project(":catalog:domain"))
//    testImplementation(project(":catalog:domain"))
//    testImplementation(project(":catalog:in-memory-persistence"))
//    testImplementation(project(":catalog:domain"))
//    testImplementation(testFixtures(project(":catalog:domain")))
//    testImplementation(project(":catalog:domain"))
//    testFixturesImplementation(testFixtures(project(":catalog:domain")))
    implementation(project(":common"))
    testImplementation(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}