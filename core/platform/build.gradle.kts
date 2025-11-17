plugins {
    alias(libs.plugins.cmp.feature.convention)
}

android {
    namespace = "com.dev.devmath.core.platform"
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.designsystem)

        }
        iosMain.dependencies {
            // iOS-specific dependencies if needed
        }
        androidMain.dependencies {
            implementation(libs.androidx.camera.core)
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.activity.compose)
            implementation(libs.jb.lifecycle.compose)
            implementation(libs.ucrop)

            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material.iconsExtended)

            implementation(libs.accompanist.permissions)
            implementation(libs.coil.kt.compose)
            implementation(libs.coil.compose)

            implementation(libs.androidx.compose.ui.tooling.preview)
        }
    }
}




