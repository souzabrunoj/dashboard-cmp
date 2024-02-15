import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.skie)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
}

version = "1.2"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {

            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.compose.ui)
            implementation(libs.compose.activity)
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.tooling.preview)

            implementation(libs.coroutines.android)
            implementation(libs.ktor.okhttp)
            implementation(libs.ktor.android)
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)

            implementation(libs.coroutines.core)

            implementation(libs.ktor.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)

            implementation(libs.koin.compose)
            implementation(libs.koin.core)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin)

            api(libs.image.loader)

            implementation(libs.kotlinx.serialization)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
        }

        iosMain.dependencies {
            implementation(libs.ktor.darwin)
            implementation(libs.koin.core)
        }
    }

    cocoapods {
        summary = "Common library for the Store starter kit"
        homepage = "https://github.com/souzabrunoj/store-kmp"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
        }
        extraSpecAttributes["swift_version"] = "\"5.0\"" // <- SKIE Needs this!
        podfile = project.file("../iosApp/Podfile")
        ios.deploymentTarget = "15"
    }
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = libs.versions.application.id.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
