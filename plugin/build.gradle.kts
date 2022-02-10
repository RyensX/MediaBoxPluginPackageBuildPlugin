plugins {
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
    id("java")
    id("java-gradle-plugin")
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.0.4")
    // https://youtrack.jetbrains.com/issue/KTIJ-3769
    //implementation("com.android.tools.build:gradle:7.1.0-alpha12")
}

val artifactName = "MediaBoxPluginPackageBuild"
val packageName = "com.su.mediabox.plugin.build"

publishing {
    publications {
        register<MavenPublication>(artifactName) {
            from(components.findByName("java"))
            version = "1.0"
            group = packageName
        }
    }

    repositories {
        //暂时发布在本地
        mavenLocal()
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

gradlePlugin {
    plugins {
        create(artifactName) {
            id = packageName
            implementationClass = "com.su.mediabox.plugin.build.MediaBoxPluginPackageBuildPlugin"
        }
    }
}