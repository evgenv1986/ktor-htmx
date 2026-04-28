plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Workout"
include("Workout.usecase")
include("catalog")
include("catalog:usecase")