plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Workout"
//include("Workout.usecase")
include("catalog")
include("catalog:usecase")
include("catalog:in-memory-persistence")
include("catalog:domain")
include("catalog:rest")
include("application")
include("common")
include("session")
include("session:usecase")
include("session:domain")
include("provider")
include("tempModule")
include("session:provider")
include("training")
include("training:usecase")
include("training:domain")
include("training:rest")