plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.testing4mp"
        compileSdk = 35
        minSdk = 24
    }

    iosArm64()

    jvm("desktop")

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines.test)

                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))

                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.activity.compose)
            }
        }
    }
}
