package com.su.mediabox.plugin.build.extensions

import org.gradle.api.Project

abstract class MediaBoxPluginBuildConfigExtension(project: Project) {

    companion object {
        const val EXT_NAME = "mediaBoxPluginBuildConfig"
    }

    var pluginName: String = project.name
    var buildClassesDir = "build\\tmp\\kotlin-classes\\release"
}