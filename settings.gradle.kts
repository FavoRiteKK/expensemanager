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

include(":app")
include(":macrobenchmark")

include(":core:common")
include(":core:common4mp")
include(":core:data")
include(":core:data4mp")
include(":core:database")
include(":core:database4mp")
include(":core:datastore")
include(":core:datastore4mp")
include(":core:designsystem")
include(":core:designsystem4mp")
include(":core:domain")
include(":core:domain4mp")
include(":core:model")
include(":core:model4mp")
include(":core:navigation")
include(":core:notification")
include(":core:repository")
include(":core:repository4mp")
include(":core:testing")
include(":core:testing4mp")

include(":feature:account")
include(":feature:analysis")
include(":feature:budget")
include(":feature:category")
include(":feature:country")
include(":feature:currency")
include(":feature:dashboard")
include(":feature:filter")
include(":feature:export")
include(":feature:onboarding")
include(":feature:reminder")
include(":feature:settings")
include(":feature:theme")
include(":feature:transaction")
include(":feature:about")
