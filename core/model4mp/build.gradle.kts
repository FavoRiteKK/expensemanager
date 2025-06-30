plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.model4mp"
        compileSdk = 35
        minSdk = 24
    }

//    iosArm64()

    jvm("desktop")

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.datetime)

                implementation(compose.components.resources)

                //e: androidx.compose.compiler.plugins.kotlin.IncompatibleComposeRuntimeVersionException:
                // The Compose Compiler requires the Compose Runtime to be on the class path, but none could be found.
                implementation(project.dependencies.platform(libs.androidx.compose.bom))

                // Compose Runtime
                implementation(libs.androidx.runtime)
            }
        }
    }

}