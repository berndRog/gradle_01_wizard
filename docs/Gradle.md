# Gradle in an Android Project

[Deutsche Version](Gradle_ger.md)

## Stage 1: Base project from the Android Studio wizard

Android Studio uses **Gradle** as its build system. Gradle compiles the source code, processes Android resources, includes libraries, runs tests, and creates APK or AAB files. The `.kts` extension indicates that the build scripts are written using **Kotlin Script**.

The `gradle_01_wizard` project has the typical structure of a project with exactly one application module:

```text
gradle_01_wizard/
├── app/
│   └── build.gradle.kts
├── gradle/
│   ├── libs.versions.toml
│   ├── gradle-daemon-jvm.properties
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── gradlew.bat
```

The version catalog and the dependencies of the `app` module have already been extended for later course examples. The basic responsibilities of the files still correspond to those of a wizard project.

## `settings.gradle.kts`: project structure

`settings.gradle.kts` is evaluated at the beginning of a build. It defines which modules belong to the project and from which repositories plugins and libraries may be downloaded.

```kotlin
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
   }
}

rootProject.name = "gradle_01_wizard"
include(":app")
```

`pluginManagement` concerns Gradle plugins. `dependencyResolutionManagement` concerns the application's libraries. `FAIL_ON_PROJECT_REPOS` deliberately prevents additional repository declarations in individual modules. `include(":app")` registers the only module in this stage.

The Foojay resolver plugin in this file helps Gradle provide a suitable Java toolchain. It is part of the build environment, not the Android app.

## Root `build.gradle.kts`: making plugins available

The `build.gradle.kts` in the project directory belongs to the root project. In the first stage, it makes plugins available without applying them to the root project:

```kotlin
plugins {
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.kotlin.compose) apply false
}
```

`apply false` means that the plugin and its version are known to the build; a module later decides whether to apply the plugin. The plugins for Kotlin Serialization and KSP that are also prepared in this project are only needed by examples that use serialization or code generation.

## `gradle/libs.versions.toml`: version catalog

The version catalog manages versions, libraries, and plugins centrally:

```toml
[versions]
kotlin = "2.4.10"

[libraries]
androidx-core-ktx = {
   module = "androidx.core:core-ktx",
   version.ref = "core"
}

[plugins]
android-application = {
   id = "com.android.application",
   version.ref = "agp"
}
```

A module can then use type-safe aliases:

```kotlin
alias(libs.plugins.android.application)
implementation(libs.androidx.core.ktx)
```

Hyphens in a TOML alias become dots when the alias is accessed. Therefore, `androidx-core-ktx` becomes `libs.androidx.core.ktx`. Not every library declared in the catalog has to be used already. Room, Koin, Coil, and Retrofit are deliberately prepared for later course stages in this example.

The Compose libraries use a **BOM** (Bill of Materials). It defines a compatible set of Compose versions, so the individual Compose entries do not require their own version numbers.

## `app/build.gradle.kts`: the application module

Each module generally has its own build file. In the `plugins` block, `app` is marked as an installable Android application. Additional plugins enable the Compose compiler, Kotlin Serialization, and KSP code generation, among other features.

The `android` block describes the Android-specific configuration:

```kotlin
android {
   namespace = "de.rogallab.mobile"
   compileSdk {
      version = release(37)
   }

   defaultConfig {
      applicationId = "de.rogallab.mobile"
      minSdk = 26
      targetSdk = 37
      versionCode = 1
      versionName = "1.0"
   }
}
```

- `namespace` defines the namespace of generated Android classes such as `R` and `BuildConfig`.
- `applicationId` uniquely identifies the installed app.
- `minSdk` specifies the oldest supported Android version.
- `targetSdk` specifies the Android version for which the app's behavior is designed and tested.
- `compileSdk` specifies which Android APIs are available during compilation.
- `versionCode` is an internally increasing version number; `versionName` is the user-visible version label.

`compileOptions` configures Java 21 for this course. `buildFeatures { compose = true }` enables Compose. `testOptions` configures local and instrumented tests.

The `dependencies` block lists the module's dependencies. Important configurations include:

| Configuration | Use |
|---|---|
| `implementation` | production code of the module |
| `testImplementation` | local JVM tests |
| `androidTestImplementation` | tests on a device or emulator |
| `debugImplementation` | debug builds only, for example Compose tooling |
| `ksp` | code generators, including Room in this project |

The `app` module in this repository already contains the complete course stack. A newly generated wizard project has significantly fewer dependencies at this point, but the purpose of the block remains the same.

## `gradle.properties`: how Gradle operates

`gradle.properties` contains project-wide properties of the build system. In this project, `org.gradle.jvmargs` limits the Gradle daemon heap to 2 GiB, among other settings. `org.gradle.configuration-cache=true` enables the configuration cache so that Gradle can skip an unchanged configuration phase in subsequent builds. `kotlin.code.style=official` selects the official Kotlin coding style.

## `local.properties`: development machine settings

`local.properties` is generated locally by Android Studio and usually contains the path to the Android SDK:

```properties
sdk.dir=/Users/.../Library/Android/sdk
```

The path differs from one computer to another. The file is therefore excluded by `.gitignore` and is not stored in the repository. Passwords and API keys should likewise not be written to version-controlled Gradle files.

## Gradle Wrapper and daemon JVM

`gradlew` is the launcher script for macOS and Linux; `gradlew.bat` is its Windows counterpart. Both use the Gradle version defined in `gradle/wrapper/gradle-wrapper.properties`. This ensures that everyone uses the same build version without having to install Gradle separately.

```bash
./gradlew test
./gradlew assembleDebug
```

`gradle-daemon-jvm.properties` describes the Java version for the Gradle daemon and contains platform-specific download sources. It must not be confused with `compileOptions`: the daemon JVM runs Gradle, while `compileOptions` defines the target for the Android module's Java source code.

## Responsibilities at a glance

| File | Key question |
|---|---|
| `settings.gradle.kts` | Which modules and repositories belong to the build? |
| Root `build.gradle.kts` | Which plugins are available project-wide? |
| `gradle/libs.versions.toml` | Which versions, libraries, and plugins are known? |
| `app/build.gradle.kts` | How is the actual Android app built? |
| `gradle.properties` | How does Gradle operate in this project? |
| `local.properties` | Which paths apply to the local development machine? |
| Gradle Wrapper | Which Gradle version is used for the build? |

The next development stage is the [`gradle_02_shared`](https://github.com/berndRog/gradle_02_shared) project. It adds an Android library module.
