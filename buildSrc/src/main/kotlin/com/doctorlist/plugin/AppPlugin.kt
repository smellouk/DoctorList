package com.doctorlist.plugin

import Config.applicationPlugin
import org.gradle.api.Project

class AppPlugin : BaseAndroidPlugin() {
    override fun apply(target: Project) {
        target.plugins.apply(applicationPlugin)
        super.apply(target)
    }
}