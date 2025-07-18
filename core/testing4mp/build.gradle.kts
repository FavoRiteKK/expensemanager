plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.testing4mp"
    }

    // Source set declarations.
    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))

                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotlin.test)
                implementation(libs.turbine)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.ext)
                implementation(libs.androidx.test.runner)
                implementation(libs.robolectric)
            }
        }

        val desktopPlusAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.truth)
            }
        }

        getByName("desktopMain") {
            dependsOn(desktopPlusAndroidMain)
        }

        androidMain.get().dependsOn(desktopPlusAndroidMain)
    }
}
