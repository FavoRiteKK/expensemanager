package com.naveenapps.expensemanager.buildsrc.plugins

import com.naveenapps.expensemanager.buildsrc.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.invoke {
                    commonMain {
                        dependencies {
                            implementation(
                                libs.findLibrary("compose.components.resources").get()
                            )

                            //e: androidx.compose.compiler.plugins.kotlin.IncompatibleComposeRuntimeVersionException:
                            // The Compose Compiler requires the Compose Runtime to be on the class path, but none could be found.
                            implementation(
                                project.dependencies.platform(
                                    libs.findLibrary("androidx.compose.bom").get()
                                )
                            )

                            // Compose Runtime
                            implementation(
                                libs.findLibrary("androidx.runtime").get()
                            )
                        }
                    }

                    androidMain {
                        dependencies {
                            implementation(libs.findLibrary("koin.androidx.compose").get())
                        }
                    }
                }
            }
        }
    }
}