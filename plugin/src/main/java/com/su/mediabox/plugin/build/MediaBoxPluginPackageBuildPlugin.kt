package com.su.mediabox.plugin.build

import com.su.mediabox.plugin.build.extensions.MediaBoxPluginBuildConfigExtension
import com.su.mediabox.plugin.build.tasks.BuildMediaBoxPluginDex
import com.su.mediabox.plugin.build.tasks.BuildMediaBoxPluginJar
import com.su.mediabox.plugin.build.tasks.BuildMediaBoxPluginPackage
import com.su.mediabox.plugin.build.tasks.CopyDependencyClasses
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.bundling.Jar

class MediaBoxPluginPackageBuildPlugin : Plugin<Project> {

    companion object {
        const val CONFIG_NAME = "embedLib"
    }

    override fun apply(target: Project) {
        target.run {
            checkAndroidLibraryPlugin()
            //添加依赖项
            configurations.create(CONFIG_NAME)
            //添加配置项
            val mediaBoxPluginBuildConfig =
                extensions.create(
                    MediaBoxPluginBuildConfigExtension.EXT_NAME,
                    MediaBoxPluginBuildConfigExtension::class.java,
                    target
                )
            //添加相关Task
            tasks.register(
                BuildMediaBoxPluginJar::class.java.simpleName,
                Jar::class.java
            ) { jar ->
                jar.pluginConfig()
                jar.description = "打包媒体插件Jar"
                jar.dependsOn(CopyDependencyClasses::class.java.simpleName)

                val pluginName = mediaBoxPluginBuildConfig.pluginName
                jar.logger.log(LogLevel.INFO, "Start build MediaBoxPluginPackage:${pluginName}")
                //删除全部jar
                delete("build/libs/${pluginName}.jar")
                //重命名目标
                jar.archiveBaseName.set(pluginName)
                jar.from(mediaBoxPluginBuildConfig.buildClassesDir)
                //排除多余文件
                jar.exclude { it.name.startsWith("R$") }
                //delete "build/libs/${pluginName}.jar"
            }
            arrayOf(
                //BuildMediaBoxPluginJar::class.java,
                CopyDependencyClasses::class.java,
                BuildMediaBoxPluginDex::class.java,
                BuildMediaBoxPluginPackage::class.java
            ).forEach {
                tasks.register(it.simpleName, it)
            }
        }
    }
}