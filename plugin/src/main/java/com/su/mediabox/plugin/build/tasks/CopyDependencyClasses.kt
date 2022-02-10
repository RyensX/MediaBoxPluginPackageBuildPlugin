package com.su.mediabox.plugin.build.tasks

import com.su.mediabox.plugin.build.extensions.MediaBoxPluginBuildConfigExtension
import com.su.mediabox.plugin.build.MediaBoxPluginPackageBuildPlugin
import com.su.mediabox.plugin.build.pluginConfig
import com.su.mediabox.plugin.build.tmpByteCodePath
import org.gradle.api.DefaultTask
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.TaskAction

abstract class CopyDependencyClasses : DefaultTask() {

    init {
        dependsOn("build")
        pluginConfig()
        description = "复制整合依赖库的字节码以静态打包"
    }

    @TaskAction
    fun run() = project.run {
        val extension = extensions.getByType(MediaBoxPluginBuildConfigExtension::class.java)
        val jarBuildDir = extension.buildClassesDir
        //把依赖的字节码和目标放在一起等待编译
        copy { copySpec ->
            copySpec.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            configurations.getByName(MediaBoxPluginPackageBuildPlugin.CONFIG_NAME).run {
                resolvedConfiguration.resolvedArtifacts.forEach { art ->
                    val libPath =
                        zipTree(art.file.absolutePath).tmpByteCodePath ?: return@forEach
                    copySpec.from(libPath)
                    copySpec.exclude { it.file.absolutePath.contains("META-INF") }
                    copySpec.into(jarBuildDir)
                }
            }
        }
    }
}