
plugins {
    alias(libs.plugins.kmp.library.convention)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.dev.devmath.core.designsystem"
}

kotlin {
    sourceSets {
        androidInstrumentedTest.dependencies {

        }
        androidUnitTest.dependencies {

        }
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.uiUtil)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.coil.kt.compose)

            api(compose.material3AdaptiveNavigationSuite)
            api(libs.jetbrains.compose.material3.adaptive)
            api(libs.jetbrains.compose.material3.adaptive.layout)
            api(libs.jetbrains.compose.material3.adaptive.navigation)
        }

        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "com.dev.devmath.core.designsystem.generated.resources"
}
