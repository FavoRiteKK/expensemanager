import com.android.build.api.dsl.androidLibrary
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mockmp)
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.domain4mp"
        compileSdk = 35
        minSdk = 24

        withHostTestBuilder { }

        /* without this, Robolectric seems not work, test events were not received */
        withDeviceTestBuilder { }

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(
                    JvmTarget.JVM_19
                )
            }
        }
    }

//    iosArm64()
    // e: Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 1.8. Please specify proper '-jvm-target' option.
    jvm("desktop")

    applyDefaultHierarchyTemplate()

    // Source set declarations.
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core:common4mp"))
                implementation(project(":core:model4mp"))
                implementation(project(":core:repository4mp"))
                implementation(project(":core:data4mp"))

                implementation(libs.kotlin.stdlib)
                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)

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

dependencies {  /* actually for test */
    // (2) instead add ksp for mockmp here
    add("kspDesktopTest", "org.kodein.mock:mockmp-processor:2.0.2")
    add("kspAndroidHostTest", "org.kodein.mock:mockmp-processor:2.0.2")
}

mockmp {
    onTest { // or onMain
        targets("this-is-not-compatible-with-agp-8.8.+")   // (1)
    }
}
