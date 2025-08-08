plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.feature")
    id("naveenapps.plugin.composeResources.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.feature.reminder"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(mapOf("path" to ":core:notification4mp")))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.androidx.lifecycle.viewModelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.androidx.navigation.compose)

                implementation(libs.kottie)
            }
        }
    }
}

compose.resources {
    publicResClass = true
}
