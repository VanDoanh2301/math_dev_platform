plugins {
    alias(libs.plugins.kmp.library.convention)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.dev.devmath.core.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
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
    }
}

compose {
    resources {
        publicResClass = true
        packageOfResClass = "com.dev.devmath.core.ui.resources"
    }
}