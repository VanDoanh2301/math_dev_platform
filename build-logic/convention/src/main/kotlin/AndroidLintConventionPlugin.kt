import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin that configures Android Lint for the application.
 */
class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                lint {
                    abortOnError = false
                    checkAllWarnings = true
                    warningsAsErrors = false
                    checkReleaseBuilds = true
                    // Disable lint checks that are not relevant for KMP projects
                    disable += setOf(
                        "MissingTranslation",
                        "ExtraTranslation",
                    )
                }
            }
        }
    }
}
