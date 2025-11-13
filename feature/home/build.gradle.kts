plugins {
    alias(libs.plugins.cmp.feature.convention)
}

android {
    namespace = "com.dev.devmath.core.feature.home"
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.camera)

            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.calf.permissions)
        }

        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling)
        }
        iosMain.dependencies {
            implementation(projects.core.platform)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


