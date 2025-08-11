plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.composeResources.multiplatform")
    id("naveenapps.plugin.room.multiplatform")
    id("naveenapps.plugin.test.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.data4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))
                implementation(project(":core:repository4mp"))
                implementation(project(":core:datastore4mp"))
                implementation(project(":core:database4mp"))

                implementation(libs.kotlinx.serialization.json)
                // DataStore library
                implementation(libs.androidx.dataStore.core)
                // The Preferences DataStore library
                implementation(libs.androidx.dataStore.preference)
                implementation(libs.filekit.dialogs.compose)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.permissions)
                implementation(libs.permissions.storage)
            }
        }
    }
}
