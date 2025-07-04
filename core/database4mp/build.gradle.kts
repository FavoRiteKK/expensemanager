plugins {
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.room.multiplatform")
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.database4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:model4mp"))
            }
        }
    }
}
