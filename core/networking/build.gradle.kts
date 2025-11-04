plugins {
    alias(libs.plugins.kmp.library.convention)
    alias(libs.plugins.ktrofit)
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.dev.devmath.core.networking"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.ktorfit.lib)

            implementation(libs.squareup.okio)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
    add("kspAndroid", libs.ktorfit.ksp)
    add("kspIosArm64", libs.ktorfit.ksp)
    add("kspIosSimulatorArm64", libs.ktorfit.ksp)
}