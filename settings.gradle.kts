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
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "expensemanager"

include(":composeApp")

include(":feature:about4mp")
include(":feature:account4mp")
include(":feature:analysis4mp")
include(":feature:budget4mp")
include(":feature:category4mp")
include(":feature:country4mp")
include(":feature:currency4mp")
include(":feature:dashboard4mp")
include(":feature:export4mp")
include(":feature:filter4mp")
include(":feature:onboarding4mp")
include(":feature:reminder4mp")
include(":feature:settings4mp")
include(":feature:theme4mp")
include(":feature:transaction4mp")

include(":core:common4mp")
include(":core:data4mp")
include(":core:database4mp")
include(":core:datastore4mp")
include(":core:designsystem4mp")
include(":core:domain4mp")
include(":core:model4mp")
include(":core:navigation4mp")
include(":core:notification4mp")
include(":core:repository4mp")
include(":core:testing4mp")
