plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {

    // Target declarations
    androidLibrary {
        namespace = "com.naveenapps.expensemanager.core.database4mp"
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
                implementation(project(":core:model4mp"))

                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
            }
        }
    }
}

dependencies {
    add("kspDesktop", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)
}
