package com.doctorlist.plugin

import Config.kotlinAndroidExtPlugin
import Config.kotlinAndroidPlugin
import Config.kotlinKaptPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BaseAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(kotlinAndroidPlugin)
        target.plugins.apply(kotlinAndroidExtPlugin)
        target.plugins.apply(kotlinKaptPlugin)
    }
}