import kotlin.String
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version.
 */
object Versions {
    const val com_github_chuckerteam_chucker: String = "3.1.2" // available: "3.2.0"

    const val com_squareup_okhttp3: String = "4.7.2"

    const val org_jetbrains_kotlin: String = "1.3.72"

    const val androidx_navigation: String = "2.2.2"

    const val com_tinder_scarlet: String = "0.1.10"

    const val com_google_dagger: String = "2.28"

    const val androidx_room: String = "2.2.5"

    const val com_android_tools_build_gradle: String = "4.0.0"

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.7.0"

    const val lifecycle_extensions: String = "2.2.0"

    const val constraintlayout: String = "1.1.3"

    const val core_testing: String = "2.1.0"

    const val recyclerview: String = "1.1.0"

    const val lint_gradle: String = "27.0.0"

    const val appcompat: String = "1.1.0"

    const val rxandroid: String = "2.1.1"

    const val cardview: String = "1.0.0"

    const val core_ktx: String = "1.3.0"

    const val material: String = "1.1.0"

    const val rxjava: String = "2.2.19"

    const val aapt2: String = "4.0.0-6051327"

    const val grgit: String = "2.1.1" // available: "2.3.0"

    const val junit: String = "4.13"

    const val mockk: String = "1.10.0"

    /**
     * Current version: "6.1.1"
     * See issue 19: How to update Gradle itself?
     * https://github.com/jmfayard/buildSrcVersions/issues/19
     */
    const val gradleLatestVersion: String = "6.5"
}

/**
 * See issue #47: how to update buildSrcVersions itself
 * https://github.com/jmfayard/buildSrcVersions/issues/47
 */
val PluginDependenciesSpec.buildSrcVersions: PluginDependencySpec
    inline get() =
        id("de.fayard.buildSrcVersions").version(Versions.de_fayard_buildsrcversions_gradle_plugin)