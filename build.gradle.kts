plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
}

// Task to run all unit tests across all modules
// Usage: ./gradlew testAllAndroid, ./gradlew testAllIos, or ./gradlew testAll
tasks.register("testAllAndroid") {
    group = "verification"
    description = "Runs all Android Debug unit tests for all modules"
    // This will automatically run testDebugUnitTest for all subprojects that have it
}

tasks.register("testAllIos") {
    group = "verification"
    description = "Runs all iOS Simulator Arm64 tests for all modules"
    // This will automatically run iosSimulatorArm64Test for all subprojects that have it
}

tasks.register("testAll") {
    group = "verification"
    description = "Runs all unit tests (Android + iOS) for all modules"
    // This will automatically run both testDebugUnitTest and iosSimulatorArm64Test for all subprojects
}