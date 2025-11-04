import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "org.convention.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradle)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.detekt.gradlePlugin)
    // Test dependencies
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.platform.junit.platform.launcher)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }

    // Configure JUnit 5 for testing
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "org.convention.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplication") {
            id = "org.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidFlavors") {
            id = "org.convention.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }

        register("androidLint") {
            id = "org.convention.android.application.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        // KMP & CMP Plugins
        register("cmpFeature") {
            id = "org.convention.cmp.feature"
            implementationClass = "CMPFeatureConventionPlugin"
        }

        register("kmpKoin") {
            id = "org.convention.kmp.koin"
            implementationClass = "KMPKoinConventionPlugin"
        }
        register("kmpLibrary") {
            id = "org.convention.kmp.library"
            implementationClass = "KMPLibraryConventionPlugin"
        }

        register("spotless") {
            id = "org.convention.spotless.plugin"
            implementationClass = "SpotlessConventionPlugin"
            description = "Configures spotless for the project"
        }

        register("kmpRoom"){
            id = "org.convention.kmp.room"
            implementationClass = "KMPRoomConventionPlugin"
            description = "Configures Room for the project"
        }
        register("detekt") {
            id = "org.convention.detekt.plugin"
            implementationClass = "DetektConventionPlugin"
            description = "Configures detekt for the project"
        }
    }
}
