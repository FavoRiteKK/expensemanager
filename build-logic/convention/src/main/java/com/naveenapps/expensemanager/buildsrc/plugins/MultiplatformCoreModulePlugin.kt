package com.naveenapps.expensemanager.buildsrc.plugins

import com.android.build.api.dsl.androidLibrary
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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
            }

            extensions.configure<KotlinMultiplatformExtension> {
                val androidKmpPlugin =
                    libs.findPlugin("android.kotlin.multiplatform.library").get().get()
                val androidAppKmp =
                    libs.findPlugin("android.application").get().get()

                if (pluginManager.hasPlugin(androidKmpPlugin.pluginId)) {
                    androidLibrary {
                        compileSdk = COMPILE_SDK
                        minSdk = MIN_SDK
                    }
                } else if (pluginManager.hasPlugin(androidAppKmp.pluginId)) {
                    androidTarget()

//                    listOf(
//                        iosX64(),
//                        iosArm64(),
//                        iosSimulatorArm64()
//                    ).forEach { iosTarget ->
//                        iosTarget.binaries.framework {
//                            baseName = "ExpenseManagerComposeApp"
//                            isStatic = true
//                        }
//                    }
                }

                // Jvm Desktop
                jvm("desktop")

                sourceSets.invoke {
                    commonMain {
                        dependencies {
                            implementation(
                                libs.findLibrary("kotlin.stdlib").get()
                            )
                            implementation(
                                libs.findLibrary("kotlinx.coroutines.core").get()
                            )
                            implementation(
                                libs.findLibrary("kotlinx.serialization.json").get()
                            )

                            implementation(
                                project.dependencies.platform(
                                    libs.findLibrary("koin.bom").get()
                                )
                            )
                            implementation(
                                libs.findLibrary("koin.core").get()
                            )
                            implementation(
                                libs.findLibrary("koin.compose").get()
                            )
                            implementation(
                                libs.findLibrary("koin.composeViewModel").get()
                            )
//                            implementation(
//                                libs.findLibrary("koin.composeViewModelNavigation").get()
//                            )
                        }
                    }
                }
            }

            configureKotlinJVM()
        }
    }
}