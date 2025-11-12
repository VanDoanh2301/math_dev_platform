plugins {
    alias(libs.plugins.kmp.library.convention)
    alias(libs.plugins.kotlinCocoapods)
}

android {
    namespace = "com.dev.devmath.shared"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Re-export UI modules using api so they are available to composeApp
            api(projects.core.ui)
            api(projects.core.designsystem)
            api(projects.feature.home)
            api(projects.core.camera)
            
            // Navigation is needed for feature.home
            implementation(libs.jb.composeNavigation)
        }

        androidMain.dependencies {

        }

        iosMain.dependencies {

        }

        jvmMain.dependencies {

        }
    }

    cocoapods {
        version = "1.0.0"
        summary = "DevMath Shared Module"
        homepage = "https://github.com/VanDoanh2301/math_dev_platform"
    }

}

