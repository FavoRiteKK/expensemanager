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

//    iosArm64()

    jvm("desktop")

    // Source set declarations.
    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines.test)
                api(libs.kotlin.test)
                api(libs.turbine)

                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))

                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.test.core)
                api(libs.androidx.test.ext)
                api(libs.androidx.test.runner)
                api(libs.robolectric)
            }
        }

        val desktopPlusAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(libs.truth)
            }
        }

        getByName("desktopMain") {
            dependsOn(desktopPlusAndroidMain)
        }

        androidMain.get().dependsOn(desktopPlusAndroidMain)
    }
}
