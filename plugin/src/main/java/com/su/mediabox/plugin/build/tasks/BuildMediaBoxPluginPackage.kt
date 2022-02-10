package com.su.mediabox.plugin.build.tasks

import com.su.mediabox.plugin.build.extensions.MediaBoxPluginBuildConfigExtension
import com.su.mediabox.plugin.build.pluginConfig
import org.gradle.api.DefaultTask
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

abstract class BuildMediaBoxPluginPackage : DefaultTask() {

    init {
        dependsOn(BuildMediaBoxPluginDex::class.java.simpleName)
        pluginConfig()
        description = "打包媒体插件包(.mpp)"
    }

    @TaskAction
    fun run() = project.run {
        exec {
            val extension = extensions.getByType(MediaBoxPluginBuildConfigExtension::class.java)
            val pluginName = extension.pluginName
            val storeDir = "${projectDir}\\build\\plugin"
            it.workingDir(storeDir)
            //删除旧的mpp
            delete("${storeDir}\\${pluginName}.mpp")
            //重命名
            it.commandLine("cmd", "/c", "ren", "classes.dex", "${pluginName}.mpp")
            logger.log(LogLevel.INFO, "Complete!!! The pluginPackage are stored in ${storeDir}")
        }
    }
}