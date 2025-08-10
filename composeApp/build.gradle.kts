import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import com.naveenapps.expensemanager.buildsrc.extensions.COMPILE_SDK
import com.naveenapps.expensemanager.buildsrc.extensions.MIN_SDK
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("naveenapps.plugin.multiplatform.feature")
    id("naveenapps.plugin.composeResources.multiplatform")
    id("org.jetbrains.compose.hot-reload") version "1.0.0-beta02"

    id("com.github.triplet.play")
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}


val keysFolderPath: String =
    if (File("${rootDir.absolutePath}/keys/credentials.properties").exists()) {
        "${rootDir.absolutePath}/keys"
    } else {
        rootDir.absolutePath
    }

fun getCredentialsFile(): File {
    val credentialFilePath = "$keysFolderPath/credentials.properties"
    return File(credentialFilePath)
}

fun getKeystoreFile(): File {
    val keystoreFilePath = "$keysFolderPath/android_keystore.jks"
    return File(keystoreFilePath)
}

fun getPlayStorePublisherFile(): File {
    val playStorePublisherFile = "$keysFolderPath/play_publish.json"
    return File(playStorePublisherFile)
}

val credentials = getCredentialsFile()
val keystore = getKeystoreFile()
if (credentials.exists() && keystore.exists()) {
    println("----- Both Keystore & Credentials available -----")
    println("----- ${credentials.absolutePath} -----")
    val properties = Properties().apply {
        load(FileInputStream(credentials))
    }

    android {
        signingConfigs {
            create("release") {
                keyAlias = properties.getProperty("KEY_ALIAS")
                storePassword = properties.getProperty("KEY_STORE_PASSWORD")
                keyPassword = properties.getProperty("KEY_PASSWORD")
                storeFile = keystore
            }
        }
    }
} else {
    println("----- Credentials not available -----")
}

val playStorePublisher = getPlayStorePublisherFile()
if (playStorePublisher.exists()) {
    println("----- Play Store Publisher available -----")
    println("----- ${playStorePublisher.absolutePath} -----")
    val track = System.getenv()["PLAYSTORE_TRACK"]
    val status = System.getenv()["PLAYSTORE_RELEASE_STATUS"]?.uppercase()
    println("----- ENV: $track & $status -----")
    val playStoreTrack = track ?: "beta"
    val playStoreReleaseStatus =
        runCatching { ReleaseStatus.valueOf(status!!) }.getOrNull() ?: ReleaseStatus.DRAFT

    println("----- $playStoreTrack & $playStoreReleaseStatus-----")

    android {
        play {
            this.serviceAccountCredentials.set(playStorePublisher)
            this.track.set(playStoreTrack)
            this.releaseStatus.set(playStoreReleaseStatus)
            println(this.serviceAccountCredentials.get().asFile.absolutePath)
        }
    }
} else {
    println("----- Publisher not available -----")
}

kotlin {
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:about4mp"))
            implementation(project(":feature:account4mp"))
            implementation(project(":feature:analysis4mp"))
            implementation(project(":feature:budget4mp"))
            implementation(project(":feature:category4mp"))
            implementation(project(":feature:country4mp"))
            implementation(project(":feature:currency4mp"))
            implementation(project(":feature:dashboard4mp"))
            implementation(project(":feature:export4mp"))
            implementation(project(":feature:filter4mp"))
            implementation(project(":feature:onboarding4mp"))
            implementation(project(":feature:reminder4mp"))
            implementation(project(":feature:settings4mp"))
            implementation(project(":feature:theme4mp"))
            implementation(project(":feature:transaction4mp"))

            //think of moving this to Compose plugin, but could not access compose property
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.preview) //it seems only android main source set in Android studio has preview capability

            implementation(libs.androidx.lifecycle.viewModelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.filekit.dialogs.compose)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.vertexai)
            implementation(libs.firebase.config)
            implementation(libs.permissions)
            implementation(libs.androidx.startup)
        }
        getByName("desktopMain") {
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {

    namespace = "com.naveenapps.expensemanager"
    compileSdk = COMPILE_SDK

    defaultConfig {
        applicationId = "com.lws.naveenapps.expensemanager"
        minSdk = MIN_SDK
        targetSdk = COMPILE_SDK
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
//            applicationIdSuffix = ".debug"
            enableUnitTestCoverage = true
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val keyStore = runCatching { signingConfigs.getByName("release") }.getOrNull()
                ?: signingConfigs.getByName("debug")

            signingConfig = keyStore
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
//    implementation(project(":core:common"))
//    implementation(project(":core:model"))
//    implementation(project(":core:designsystem"))
//    implementation(project(":core:domain"))
//    implementation(project(":core:navigation"))
//    implementation(project(":core:notification"))
//    implementation(project(":core:repository"))
//
//    implementation(project(":feature:account"))
//    implementation(project(":feature:analysis"))
//    implementation(project(":feature:budget"))
//    implementation(project(":feature:category"))
//    implementation(project(":feature:dashboard"))
//    implementation(project(":feature:transaction"))
//    implementation(project(":feature:onboarding"))
//
//    implementation(project(":feature:settings"))
//    implementation(project(":feature:theme"))
//    implementation(project(":feature:export"))
//    implementation(project(":feature:reminder"))
//    implementation(project(":feature:currency"))
//    implementation(project(":feature:about"))
//
//    debugImplementation(compose.uiTooling)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.crashlytics)
//    implementation(libs.firebase.analytics)
//
//    implementation(libs.androidx.splash.screen)
//
//    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.material)
//    implementation(libs.androidx.profileinstaller)
//
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.hilt.ext.work)
//
//    implementation(libs.google.oss.licenses)
//
//    implementation(libs.app.update.ktx)
//
//    testImplementation(project(":core:testing"))
//    androidTestImplementation(project(":core:testing"))
}

compose.desktop {
    application {
        mainClass = "com.naveenapps.expensemanager.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.naveenapps.expensemanager"
            packageVersion = "1.0.0"
        }
    }
}

