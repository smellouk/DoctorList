package com.doctorlist.plugin

import Config.libraryPlugin
import org.gradle.api.Project

class AndroidModulePlugin : BaseAndroidPlugin() {
    override fun apply(target: Project) {
        target.plugins.apply(libraryPlugin)
        super.apply(target)
    }
}