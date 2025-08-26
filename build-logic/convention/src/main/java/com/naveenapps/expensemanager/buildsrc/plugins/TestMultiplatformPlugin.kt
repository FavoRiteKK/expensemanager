package com.naveenapps.expensemanager.buildsrc.plugins

import com.android.build.api.dsl.androidLibrary
import com.naveenapps.expensemanager.buildsrc.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.kodein.mock.gradle.MocKMPGradlePlugin

@Suppress("UnstableApiUsage")
class TestMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("ksp").get().get().pluginId)
                apply(libs.findPlugin("mockmp").get().get().pluginId)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidLibrary {
                    withHostTestBuilder { }

                    /* without this, Robolectric seems not work, test events were not received */
                    withDeviceTestBuilder { }
                }

                sourceSets.invoke {
                    commonTest {
                        dependencies {
                            implementation(project(":core:testing4mp"))

                            implementation(
                                libs.findLibrary("kotlinx.coroutines.test").get()
                            )
                            implementation(
                                libs.findLibrary("kotlin.test").get()
                            )
                            implementation(
                                libs.findLibrary("koin.test").get()
                            )
                            implementation(
                                libs.findLibrary("turbine").get()
                            )
                        }
                    }
                    getByName("androidHostTest") {
                        dependencies {
                            implementation(
                                libs.findLibrary("androidx.test.core").get()
                            )
                            implementation(
                                libs.findLibrary("androidx.test.ext").get()
                            )
                            implementation(
                                libs.findLibrary("androidx.test.runner").get()
                            )
                            implementation(
                                libs.findLibrary("robolectric").get()
                            )
                        }
                    }
                }
            }

            dependencies {
                // (2) instead add ksp for mockmp here
                add("kspDesktopTest", "org.kodein.mock:mockmp-processor:2.0.2")
                add("kspAndroidHostTest", "org.kodein.mock:mockmp-processor:2.0.2")
                //    add("kspIosArm64", roomCompiler)
            }

            extensions.configure<MocKMPGradlePlugin.Extension> {
                onTest { // or onMain
                    targets("this-is-not-compatible-with-agp-8.8.+")   // (1)
                }
            }
        }
    }
}