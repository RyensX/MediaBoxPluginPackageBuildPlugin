plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.su.mediabox.plugin.build")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

mediaBoxPluginBuildConfig {
    pluginName = "TestPlugin"
}

dependencies {
    val jsoup = "org.jsoup:jsoup:1.14.3"
    implementation(jsoup)
    embedLib(jsoup)
    val eventbus = "org.greenrobot:eventbus:3.2.0"
    implementation(eventbus)
    embedLib(eventbus)
}