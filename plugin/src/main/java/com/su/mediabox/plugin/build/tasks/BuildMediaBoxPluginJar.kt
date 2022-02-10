package com.su.mediabox.plugin.build.tasks

import com.su.mediabox.plugin.build.extensions.MediaBoxPluginBuildConfigExtension
import com.su.mediabox.plugin.build.pluginConfig
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

/**
 * 打包媒体扩展包Jar
 * 注意：暂时无法使用，因为直接继承Jar实现的Task无效
 */
abstract class BuildMediaBoxPluginJar : Jar() {

    init {
        dependsOn("build")
        pluginConfig()
        description = "打包媒体插件Jar"
    }

    @TaskAction
    fun run() = project.run {
        val extension = extensions.getByType(MediaBoxPluginBuildConfigExtension::class.java)
        val pluginName = extension.pluginName
        logger.log(LogLevel.INFO, "Start build MediaBoxPluginPackage:${pluginName}")
        //先删除旧的jar防止错误
        delete("build/libs/${pluginName}.jar")
        //重命名目标
        archiveBaseName.set(pluginName)
        from("build\\tmp\\kotlin-classes\\release")
        //排除多余文件
        exclude { it.name.startsWith("R$") }
        //delete "build/libs/${pluginName}.jar"
    }
}