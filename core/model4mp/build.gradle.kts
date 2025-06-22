plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.model4mp"
        compileSdk = 35
        minSdk = 24
    }

    iosArm64()

    jvm("desktop")

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.datetime)
            }
        }
    }

}