dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "MediaBoxPluginPackageBuildPlugin"
include (":app")
include(":plugin")
include(":TestMmp")
include(":TestMpp2")
include(":TestMpp3")
