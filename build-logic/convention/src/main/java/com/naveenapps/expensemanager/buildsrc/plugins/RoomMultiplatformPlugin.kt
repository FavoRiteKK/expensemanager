package com.naveenapps.expensemanager.buildsrc.plugins

import androidx.room.gradle.RoomExtension
import com.naveenapps.expensemanager.buildsrc.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("ksp").get().get().pluginId)
                apply(libs.findPlugin("androidx.room").get().get().pluginId)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.invoke {
                    commonMain {
                        dependencies {
                            implementation(
                                libs.findLibrary("room.runtime").get()
                            )
                            implementation(
                                libs.findLibrary("sqlite.bundled").get()
                            )
                        }
                    }
                }
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                val roomCompiler = libs.findLibrary("room.compiler").get()
                add("kspDesktop", roomCompiler)
                add("kspAndroid", roomCompiler)
                //    add("kspIosArm64", roomCompiler)
            }
        }
    }
}