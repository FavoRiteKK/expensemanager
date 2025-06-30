plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.mockmp)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.data4mp"
        compileSdk = 35
        minSdk = 24

        withHostTestBuilder { }

        /* without this, Robolectric seems not work, test events were not received */
        withDeviceTestBuilder { }
    }

//    iosArm64()

    jvm("desktop")

    applyDefaultHierarchyTemplate()

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))
                implementation(project(":core:repository4mp"))
                implementation(project(":core:datastore4mp"))
                implementation(project(":core:database4mp"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)

                // DataStore library
                implementation(libs.androidx.dataStore.core)
                // The Preferences DataStore library
                implementation(libs.androidx.dataStore.preference)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.kotlinx.serialization.json)
                implementation(compose.components.resources)

                //e: androidx.compose.compiler.plugins.kotlin.IncompatibleComposeRuntimeVersionException:
                // The Compose Compiler requires the Compose Runtime to be on the class path, but none could be found.
                implementation(project.dependencies.platform(libs.androidx.compose.bom))

                // Compose Runtime
                implementation(libs.androidx.runtime)
            }
        }

        commonTest {
            dependencies {
                implementation(project(":core:testing4mp"))

                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {  /* actually for test */
    add("kspDesktop", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)

    // (2) instead add ksp for mockmp here
    add("kspDesktopTest", "org.kodein.mock:mockmp-processor:2.0.2")
    add("kspAndroidHostTest", "org.kodein.mock:mockmp-processor:2.0.2")
}

mockmp {
    onTest { // or onMain
        targets("this-is-not-compatible-with-agp-8.8.+")   // (1)
    }
}
