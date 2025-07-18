plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.navigation4mp"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.androidx.navigation.compose)
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}