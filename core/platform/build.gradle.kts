plugins {
    alias(libs.plugins.cmp.feature.convention)
}

android {
    namespace = "com.dev.devmath.core.platform"
}

kotlin {

    sourceSets {
        iosMain.dependencies {
            // iOS-specific dependencies if needed
        }
    }
}


