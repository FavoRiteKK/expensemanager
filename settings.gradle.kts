pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://jitpack.io") }
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "expensemanager"

include(":core:common4mp")
include(":core:data4mp")
include(":core:database4mp")
include(":core:datastore4mp")
include(":core:designsystem4mp")
include(":core:domain4mp")
include(":core:model4mp")
include(":core:repository4mp")
include(":core:testing4mp")
