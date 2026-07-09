plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

private val gitCommitsCount: Int by lazy {
    runCatching {
        ProcessBuilder("git", "rev-list", "--count", "HEAD")
            .directory(rootDir)
            .redirectErrorStream(true)
            .start().inputStream.bufferedReader().readLine().trim().toInt()
    }.getOrDefault(1)
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

android {
    namespace = "org.michaelbel.fontscale"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "org.michaelbel.fontscale"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = gitCommitsCount
        versionName = "1.0.0"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "myapplication"
            keyPassword = "password"
            storeFile = rootProject.file(".github/debug-key.jks")
            storePassword = "password"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
}

base {
    archivesName.set("FontScale-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode})")
}

dependencies {
    implementation(libs.google.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.splashscreen)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

tasks.register("printVersion") {
    description = "Prints the current versionName and versionCode to stdout."
    doLast {
        println("VERSION_NAME=${android.defaultConfig.versionName}")
        println("VERSION_CODE=${android.defaultConfig.versionCode}")
    }
}
