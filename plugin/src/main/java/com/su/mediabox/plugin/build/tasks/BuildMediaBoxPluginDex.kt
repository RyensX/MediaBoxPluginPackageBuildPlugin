package com.su.mediabox.plugin.build.tasks

import com.android.build.gradle.BaseExtension
import com.su.mediabox.plugin.build.extensions.MediaBoxPluginBuildConfigExtension
import com.su.mediabox.plugin.build.pluginConfig
import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

abstract class BuildMediaBoxPluginDex : DefaultTask() {

    init {
        dependsOn(BuildMediaBoxPluginJar::class.java.simpleName)
        pluginConfig()
        description = "打包媒体插件Dex"
    }

    @Suppress("PrivateApi")
    @TaskAction
    fun run() = project.run {
        exec { execSpec ->
            val extension = extensions.getByType(MediaBoxPluginBuildConfigExtension::class.java)
            val pluginName = extension.pluginName
            val storeDir = "build\\plugin"
            val projectDir = projectDir.absolutePath
            mkdir("build\\plugin")
            execSpec.workingDir(projectDir)

            runCatching {
                extensions.getByType(BaseExtension::class.java)
            }.onSuccess { android ->
                val d8Dir =
                    "${android.sdkDirectory}\\build-tools\\${android.buildToolsVersion}\\d8.bat"
                logger.log(LogLevel.INFO, "d8Dir=${d8Dir}")
                //暂时只支持Windows
                execSpec.commandLine(
                    "cmd", "/c", d8Dir,
                    "build\\libs\\${pluginName}.jar", "--output=${storeDir}", "--release"
                )
            }.onFailure {
                it.printStackTrace()
                throw RuntimeException(it.message)
            }
        }

    }
}