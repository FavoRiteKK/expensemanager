plugins {
    id("naveenapps.plugin.multiplatform.core")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.datastore4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:model4mp"))

                // DataStore library
                api(libs.androidx.dataStore.core)
                // The Preferences DataStore library
                api(libs.androidx.dataStore.preference)
            }
        }
    }
}