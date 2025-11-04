plugins {
    alias(libs.plugins.kmp.library.convention)
    alias(libs.plugins.cmp.feature.convention)
    alias(libs.plugins.kotlinCocoapods)
}

android {
    namespace = "com.dev.devmath.shared"

}

kotlin {
    sourceSets {
        commonMain.dependencies {

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

