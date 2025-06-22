plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.datastore4mp"
        compileSdk = 35
        minSdk = 24
    }

    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:model4mp"))
                implementation(project(":core:common4mp"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.datetime)
                implementation(libs.sqldelight.coroutines.extensions)

                api(libs.multiplatformSettings.coroutines)
                api(libs.koin.core)
                implementation(libs.koin.compose)

                // DataStore library
                implementation(libs.androidx.dataStore.core)
                // The Preferences DataStore library
                implementation(libs.androidx.dataStore.preference)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
            }
        }
    }

}