import com.android.build.api.dsl.ApplicationExtension
import org.convention.configureAndroidCompose
import org.convention.configureGradleManagedDevices
import org.convention.configureKotlinAndroid
import org.convention.configureKotlinMultiplatform
import org.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Plugin that configures a Compose Multiplatform Application.
 * This plugin applies:
 * - Kotlin Multiplatform with Android, iOS, and JVM targets
 * - Android Application with Compose
 * - Compose Multiplatform
 * - Code quality plugins (Detekt, Spotless)
 */
class CMPApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
                apply("org.convention.detekt.plugin")
                apply("org.convention.spotless.plugin")
            }

            // Configure Kotlin Multiplatform
            configureKotlinMultiplatform()

            // Configure Android
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                defaultConfig.targetSdk = 36
                @Suppress("UnstableApiUsage")
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

        }
    }
}