plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.composeResources.multiplatform")
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
                implementation(project(":core:common4mp"))
                implementation(project(":core:data4mp"))
                implementation(project(":core:repository4mp"))  //for highlight
                implementation(project(":core:model4mp"))
            }
        }
        commonTest {
            dependencies {
                implementation(libs.slf4j.simple)
            }
        }
    }
}
