plugins {
    kotlin("jvm")
}

group = "ru.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catalog:domain"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}