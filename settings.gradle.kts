// Central plugin repository configuration for the complete Gradle build.
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

// Automatically provisions a matching Java toolchain when necessary.
plugins {
   id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// All modules resolve their external libraries from the same repositories.
dependencyResolutionManagement {

   // Module-specific repository declarations are rejected deliberately.
   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

   repositories {
      google()
      mavenCentral()
   }
}

// Name of the complete Gradle project.
rootProject.name = "gradle_01_wizard"

// The master branch represents the regular single-module wizard project.
include(":app")
