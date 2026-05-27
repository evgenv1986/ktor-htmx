project.base.archivesName.set("workout-provider")

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
    implementation(project(":training:usecase"))
    implementation(project(":training:domain"))
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.kotest:kotest-framework-engine:5.9.1")
    testImplementation(kotlin("test"))
    testFixturesImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testFixturesImplementation("io.arrow-kt:arrow-core:2.2.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}