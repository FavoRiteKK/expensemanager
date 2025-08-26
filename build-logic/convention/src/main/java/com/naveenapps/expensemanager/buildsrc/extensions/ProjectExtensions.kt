package com.naveenapps.expensemanager.buildsrc.extensions

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

@Suppress("UnstableApiUsage")
fun Project.configureBasicMultiplatformExtension() {
    extensions.configure<KotlinMultiplatformExtension> {
        val androidKmpPlugin =
            libs.findPlugin("android.kotlin.multiplatform.library").get().get()
        val androidAppKmp =
            libs.findPlugin("android.application").get().get()

        if (pluginManager.hasPlugin(androidKmpPlugin.pluginId)) {
            androidLibrary {
                compileSdk = COMPILE_SDK
                minSdk = MIN_SDK

                experimentalProperties["android.experimental.kmp.enableAndroidResources"] =
                    true
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
                        libs.findLibrary("kotlinx.datetime").get()
                    )

                    implementation(
                        libs.findLibrary("kotlin.logging").get()
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
                    implementation("de.drick.compose:hotpreview:0.2.0")
                }
            }
            getByName("desktopMain") {
                dependencies {
                    implementation(
                        libs.findLibrary("slf4j.api").get()
                    )
                    implementation(
                        libs.findLibrary("slf4j.simple").get()
                    )
                }
            }
            androidMain {
                dependencies {
                    implementation(
                        libs.findLibrary("slf4j.api").get()
                    )
                    implementation(
                        libs.findLibrary("slf4j.simple").get()
                    )
                }
            }
        }
    }
}

fun Project.configureFeatureMultiplatformExtension() {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.invoke {
            commonMain {
                dependencies {
                    implementation(project(":core:common4mp"))
                    implementation(project(":core:data4mp"))
                    implementation(project(":core:datastore4mp"))
                    implementation(project(":core:database4mp"))
                    implementation(project(":core:designsystem4mp"))
                    implementation(project(":core:domain4mp"))
                    implementation(project(":core:model4mp"))
                    implementation(project(":core:navigation4mp"))
                    implementation(project(":core:notification4mp"))
                    implementation(project(":core:repository4mp"))

                    implementation(
                        libs.findLibrary("alert.kmp").get()
                    )
                }
            }
        }
    }
}
