plugins {
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.compose.multiplatform")
    id("naveenapps.plugin.room.multiplatform")
    id("naveenapps.plugin.test.multiplatform")
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
                api(project(":core:repository4mp"))
                api(project(":core:datastore4mp"))
                api(project(":core:database4mp"))
            }
        }
    }
}
