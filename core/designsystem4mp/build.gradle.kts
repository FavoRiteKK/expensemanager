plugins {
    id("naveenapps.plugin.multiplatform.core")
    id("naveenapps.plugin.compose.multiplatform")
//    id("naveenapps.plugin.test.multiplatform")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.designsystem4mp"

        withHostTestBuilder { }

        /* without this, Robolectric seems not work, test events were not received */
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }
    }

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:model4mp"))

                api(libs.androidx.appcompat)
                implementation(libs.androidx.compose.activity)
                implementation(libs.androidx.core.ktx)

                api(libs.androidx.compose.animation)
                api(libs.androidx.compose.foundation)
                api(libs.androidx.compose.foundation.layout)
                api(libs.androidx.compose.material.iconsExtended)
                api(libs.androidx.compose.material3)
                api(libs.androidx.compose.runtime)
                api(libs.androidx.compose.runtime.livedata)
                api(libs.androidx.compose.runtime.tracing)
                api(libs.androidx.compose.ui.tooling.preview)
                api(libs.androidx.compose.ui.util)
                api(libs.androidx.metrics)
                api(libs.androidx.tracing.ktx)
                api(libs.androidx.navigation.compose)
                api(libs.androidx.hilt.navigation.compose)
                api(libs.accompanist.permissions)
                api(libs.accompanist.systemUIController)

//                debugApi(libs.androidx.compose.ui.tooling)

                api(libs.mpcharts)
            }
        }
//
//        commonTest {
//            dependencies {
//                implementation(project(":core:testing4mp"))
//
//                implementation(
//                    libs.kotlinx.coroutines.test
//                )
//                implementation(
//                    libs.kotlin.test
//                )
//                implementation(
//                    libs.koin.test
//                )
//                implementation(
//                    libs.turbine
//                )
//            }
//        }
    }
}