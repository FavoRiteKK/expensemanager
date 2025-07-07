package com.naveenapps.expensemanager.buildsrc.plugins

import com.naveenapps.expensemanager.buildsrc.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeResourceMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("compose.compiler").get().get().pluginId)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.invoke {
                    commonMain {
                        dependencies {
                            implementation(
                                libs.findLibrary("compose.components.resources").get()
                            )

                            // Compose Runtime
                            implementation(
                                libs.findLibrary("compose.runtime").get()
                            )
                        }
                    }
                }
            }
        }
    }
}