plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.repository4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))
            }
        }
    }
}