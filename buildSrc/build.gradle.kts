plugins {
    `kotlin-dsl`
}

object PluginVersion {
    const val android = "4.0.0"
    const val kotlin = "1.3.72"
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginVersion.android}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.kotlin}")
}

gradlePlugin {
    plugins {
        register("AppPlugin") {
            id = "doctor-list-app"
            implementationClass = "com.doctorlist.plugin.AppPlugin"
        }
        register("AndroidModulePlugin") {
            id = "doctor-list-android-module"
            implementationClass = "com.doctorlist.plugin.AndroidModulePlugin"
        }
        register("KotlinModulePlugin") {
            id = "doctor-list-kotlin-module"
            implementationClass = "com.doctorlist.plugin.KotlinModulePlugin"
        }
    }
}