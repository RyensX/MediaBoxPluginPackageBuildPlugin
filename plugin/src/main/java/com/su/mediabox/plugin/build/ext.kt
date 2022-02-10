package com.su.mediabox.plugin.build

import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.Task
import org.gradle.api.file.FileTree

fun Task.pluginConfig() {
    group = "MediaBoxPlugin"
}

@Suppress("UNCHECKED_CAST")
fun <T> Class<*>.invokeDeclaredMethod(obj: Any, name: String, vararg args: Any) =
    getDeclaredMethod(name).run {
        isAccessible = true
        invoke(obj, *args)
    } as T

@Suppress("UNCHECKED_CAST")
fun <T> Class<*>.getProperty(obj: Any, name: String) = try {
    getDeclaredField(name).run {
        isAccessible = true
        get(obj)
    }
} catch (e: Exception) {
    //兼容Kotlin合并setter/getter
    getDeclaredMethod("get" + name.replaceFirstChar { it.uppercase() }).run {
        isAccessible = true
        invoke(obj)
    } as T
}

val FileTree.tmpByteCodePath: String?
    get() = run {
        val k = "META-INF"
        var path: String? = null
        var p = ""
        var index = -1
        find {
            p = it.absolutePath
            index = p.indexOf(k)
            index > -1
        }?.also { path = p.substring(0, index) }
        path
    }

fun Project.checkAndroidLibraryPlugin() {
    val androidLibraryPluginId = "com.android.library"
    if (!plugins.hasPlugin(androidLibraryPluginId)) {
        throw ProjectConfigurationException(
            "PluginProject must be applied in project that has $androidLibraryPluginId!!!",
            Throwable()
        )
    }
}