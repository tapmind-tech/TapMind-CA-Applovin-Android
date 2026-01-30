pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/ravirising26/CustomeAdapter-admob")
        }
    }
}

rootProject.name = "TapMind"
include(":app")
include(":TapMindSdk")
include(":customadapter-facebook")
include(":customadapter-admob")
include(":customadapter-applovin")
include(":customadapter-ironsource")
