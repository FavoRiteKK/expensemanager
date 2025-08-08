import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.naveenapps.expensemanager.buildlogic"

// Configure the build-logic plugins to target JDK 19
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_19.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.mockmp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("MultiplatformCoreModulePlugin") {
            id = "naveenapps.plugin.multiplatform.core"
            implementationClass =
                "com.naveenapps.expensemanager.buildsrc.plugins.MultiplatformCoreModulePlugin"
        }
        create("MultiplatformFeatureModulePlugin") {
            id = "naveenapps.plugin.multiplatform.feature"
            implementationClass =
                "com.naveenapps.expensemanager.buildsrc.plugins.MultiplatformFeatureModulePlugin"
        }
        create("ComposeResourceMultiplatformPlugin") {
            id = "naveenapps.plugin.composeResources.multiplatform"
            implementationClass =
                "com.naveenapps.expensemanager.buildsrc.plugins.ComposeResourceMultiplatformPlugin"
        }
        create("RoomMultiplatformPlugin") {
            id = "naveenapps.plugin.room.multiplatform"
            implementationClass =
                "com.naveenapps.expensemanager.buildsrc.plugins.RoomMultiplatformPlugin"
        }
        create("TestMultiplatformPlugin") {
            id = "naveenapps.plugin.test.multiplatform"
            implementationClass =
                "com.naveenapps.expensemanager.buildsrc.plugins.TestMultiplatformPlugin"
        }
    }
}
