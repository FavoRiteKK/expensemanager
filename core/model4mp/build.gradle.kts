plugins {
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.compose.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.model4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:common4mp"))
            }
        }
    }

}