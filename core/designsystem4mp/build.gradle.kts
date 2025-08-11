plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.composeResources.multiplatform")
    id("naveenapps.plugin.test.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.designsystem4mp"
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:data4mp"))
                implementation(project(":core:model4mp"))
                implementation(project(":core:common4mp"))

                //think of moving this to Compose plugin, but could not access compose property
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.lifecycle.viewModelCompose)
                implementation(libs.lifecycle.runtimeCompose)

                implementation(libs.koalaplot.core)
            }
        }
    }
}

compose.resources {
    publicResClass = true
}
