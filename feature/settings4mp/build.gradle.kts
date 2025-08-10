plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.feature")
    id("naveenapps.plugin.composeResources.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.feature.settings"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":feature:export4mp"))
                implementation(project(":feature:filter4mp"))
                implementation(project(":feature:reminder4mp"))
                implementation(project(":feature:theme4mp"))
                implementation(project(":feature:about4mp"))

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
                implementation(libs.filekit.dialogs.compose)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.permissions)
                implementation(libs.permissions.storage)
                implementation(libs.permissions.notifications)
                implementation(libs.permissions.compose)
            }
        }
    }
}

compose.resources {
    publicResClass = true
}
