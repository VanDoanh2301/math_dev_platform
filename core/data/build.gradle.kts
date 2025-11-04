plugins {
    alias(libs.plugins.kmp.library.convention)
}

android {
    namespace = "com.dev.devmath.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.networking)

            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.connectivity.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.tracing.ktx)
            implementation(libs.koin.android)
        }


    }
}