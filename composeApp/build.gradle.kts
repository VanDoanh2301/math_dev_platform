import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.cmp.application.convention)
    alias(libs.plugins.composeHotReload)
}

android {
    namespace = "com.dev.devmath"

    defaultConfig {
        applicationId = "com.dev.devmath"
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    
    // Configure flavors to match shared module
    flavorDimensions += "contentType"
    productFlavors {
        create("demo") {
            dimension = "contentType"
            applicationIdSuffix = ".demo"
        }
        create("prod") {
            dimension = "contentType"
        }
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export(projects.core.platform)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            
            // Depend on shared module which aggregates all UI modules
            implementation(projects.shared)
            
            // Navigation is still needed for composeApp
            implementation(libs.jb.composeNavigation)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        iosMain.dependencies {
            api(projects.core.platform)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.dev.devmath.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dev.devmath"
            packageVersion = "1.0.0"
        }
    }
}
