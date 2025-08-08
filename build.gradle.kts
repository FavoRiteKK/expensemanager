// Lists all plugins used throughout the project without applying them.
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.google.oss.licenses.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.play.publish) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.mockmp) apply false
    alias(libs.plugins.google.services) apply false
    id("org.jetbrains.compose.hot-reload") version "1.0.0-beta02" apply false
}
