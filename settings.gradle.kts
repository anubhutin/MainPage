pluginManagement {
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
      //  maven { url "https://jitpack.io" }
       // maven { url 'https://dl.bintray.com/ssynhtn-org/android-wave-view' }
    }
}

rootProject.name = "MainPage"
include(":app")
 