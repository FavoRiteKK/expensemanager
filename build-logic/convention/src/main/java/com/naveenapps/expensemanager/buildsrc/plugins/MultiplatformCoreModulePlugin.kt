package com.naveenapps.expensemanager.buildsrc.plugins

import com.android.build.api.dsl.androidLibrary
import com.naveenapps.expensemanager.buildsrc.extensions.COMPILE_SDK
import com.naveenapps.expensemanager.buildsrc.extensions.MIN_SDK
import com.naveenapps.expensemanager.buildsrc.extensions.configureKotlinJVM
import com.naveenapps.expensemanager.buildsrc.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UnstableApiUsage")
class MultiplatformCoreModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidLibrary {
                    compileSdk = COMPILE_SDK
                    minSdk = MIN_SDK
                }

                //    val xcfName = "core:common4mpKit"
                //
                //    iosX64 {
                //        binaries.framework {
                //            baseName = xcfName
                //        }
                //    }
                //
                //    iosArm64 {
                //        binaries.framework {
                //            baseName = xcfName
                //        }
                //    }
                //
                //    iosSimulatorArm64 {
                //        binaries.framework {
                //            baseName = xcfName
                //        }
                //    }

                //    iosArm64()

                // Jvm Desktop
                jvm("desktop")

                sourceSets.invoke {
                    commonMain {
                        dependencies {
                            implementation(libs.findLibrary("kotlin.stdlib").get())
                            implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                            implementation(libs.findLibrary("kotlinx.serialization.json").get())

                            implementation(
                                project.dependencies.platform(
                                    libs.findLibrary("koin.bom").get()
                                )
                            )
                            implementation(libs.findLibrary("koin.core").get())
                            implementation(libs.findLibrary("koin.compose").get())
                            implementation(libs.findLibrary("koin.composeViewModel").get())
                            implementation(
                                libs.findLibrary("koin.composeViewModelNavigation").get()
                            )
                        }
                    }
                }
            }
            configureKotlinJVM()
        }
    }
}