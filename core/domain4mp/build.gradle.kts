plugins {
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.compose.multiplatform")
    id("naveenapps.plugin.test.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.domain4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:data4mp"))
            }
        }
    }
}
