# Gradle in einem Android-Projekt

## Stufe 1: Basisprojekt aus dem Android-Studio-Wizard

Android Studio verwendet **Gradle** als Build-System. Gradle übersetzt den Quellcode, verarbeitet Android-Ressourcen, bindet Bibliotheken ein, führt Tests aus und erzeugt APK- beziehungsweise AAB-Dateien. Die Endung `.kts` bedeutet, dass die Buildskripte mit **Kotlin Script** geschrieben sind.

Das Projekt `gradle_01_wizard` besitzt die typische Struktur eines Projekts mit genau einem Application-Modul:

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

Der Version Catalog und die Abhängigkeiten des `app`-Moduls sind für die späteren Kursbeispiele bereits erweitert. Die grundsätzliche Aufgabenverteilung der Dateien entspricht weiterhin dem Wizard-Projekt.

## `settings.gradle.kts`: Aufbau des Projekts

`settings.gradle.kts` wird zu Beginn eines Builds ausgewertet. Die Datei legt fest, welche Module zum Projekt gehören und aus welchen Repositories Plugins und Bibliotheken geladen werden dürfen.

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

`pluginManagement` betrifft Gradle-Plugins. `dependencyResolutionManagement` betrifft die Bibliotheken des Programms. Mit `FAIL_ON_PROJECT_REPOS` werden zusätzliche Repository-Angaben in einzelnen Modulen absichtlich verhindert. `include(":app")` registriert das einzige Modul dieses Standes.

Das Foojay-Resolver-Plugin in dieser Datei unterstützt Gradle dabei, eine passende Java-Toolchain bereitzustellen. Es gehört zur Buildumgebung und nicht zur Android-App.

## Root-`build.gradle.kts`: Plugins bereitstellen

Das `build.gradle.kts` im Projektverzeichnis gehört zum Root-Projekt. Im ersten Stand stellt es Plugins bereit, wendet sie aber nicht auf das Root-Projekt an:

```kotlin
plugins {
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.kotlin.compose) apply false
}
```

`apply false` bedeutet: Plugin und Version sind dem Build bekannt; ein Modul entscheidet später selbst, ob es das Plugin verwendet. Die im Projekt zusätzlich vorbereiteten Plugins für Kotlin Serialization und KSP werden erst von Beispielen benötigt, die Serialisierung oder Codegenerierung verwenden.

## `gradle/libs.versions.toml`: Version Catalog

Der Version Catalog verwaltet Versionen, Bibliotheken und Plugins zentral:

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

Ein Modul verwendet danach nur noch typsichere Aliase:

```kotlin
alias(libs.plugins.android.application)
implementation(libs.androidx.core.ktx)
```

Bindestriche im TOML-Alias werden beim Zugriff zu Punkten. Aus `androidx-core-ktx` wird daher `libs.androidx.core.ktx`. Nicht jede im Catalog deklarierte Bibliothek muss bereits verwendet werden. Im Beispiel sind Room, Koin, Coil und Retrofit bewusst für spätere Vorlesungsstufen vorbereitet.

Die Compose-Bibliotheken verwenden eine **BOM** (Bill of Materials). Sie legt zusammenpassende Compose-Versionen fest, sodass die einzelnen Compose-Einträge keine eigene Versionsnummer benötigen.

## `app/build.gradle.kts`: das Application-Modul

Jedes Modul besitzt grundsätzlich eine eigene Builddatei. Im `plugins`-Block wird `app` als installierbare Android-Anwendung gekennzeichnet. Weitere Plugins aktivieren unter anderem den Compose-Compiler, Kotlin Serialization und die KSP-Codegenerierung.

Der `android`-Block beschreibt die Android-spezifische Konfiguration:

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

- `namespace` bestimmt den Namensraum der generierten Android-Klassen, beispielsweise `R` und `BuildConfig`.
- `applicationId` identifiziert die installierte App eindeutig.
- `minSdk` legt die kleinste unterstützte Android-Version fest.
- `targetSdk` bestimmt, für welche Android-Version das Verhalten der App ausgelegt und getestet ist.
- `compileSdk` legt fest, welche Android-APIs beim Übersetzen verfügbar sind.
- `versionCode` ist die intern steigende Versionsnummer; `versionName` ist die sichtbare Versionsbezeichnung.

`compileOptions` stellt für diesen Kurs Java 21 ein. `buildFeatures { compose = true }` aktiviert Compose. `testOptions` konfiguriert lokale beziehungsweise instrumentierte Tests.

Im `dependencies`-Block stehen die Abhängigkeiten des Moduls. Wichtige Konfigurationen sind:

| Konfiguration | Verwendung |
|---|---|
| `implementation` | Produktionscode des Moduls |
| `testImplementation` | lokale JVM-Tests |
| `androidTestImplementation` | Tests auf Gerät oder Emulator |
| `debugImplementation` | nur Debug-Builds, zum Beispiel Compose-Tooling |
| `ksp` | Codegeneratoren, hier unter anderem Room |

Das `app`-Modul dieses Repositories enthält bereits den vollständigen Kurs-Stack. Ein frisch erzeugtes Wizard-Projekt besitzt an dieser Stelle deutlich weniger Abhängigkeiten; die Bedeutung des Blocks bleibt jedoch gleich.

## `gradle.properties`: Arbeitsweise von Gradle

`gradle.properties` enthält projektweite Eigenschaften des Build-Systems. In diesem Projekt begrenzt `org.gradle.jvmargs` unter anderem den Heap des Gradle-Daemons auf 2 GiB. `org.gradle.configuration-cache=true` aktiviert den Configuration Cache, damit Gradle eine unveränderte Konfigurationsphase bei späteren Builds überspringen kann. `kotlin.code.style=official` wählt den offiziellen Kotlin-Codestil.

## `local.properties`: Einstellungen des Entwicklungsrechners

`local.properties` wird lokal von Android Studio erzeugt und enthält typischerweise den Pfad zum Android SDK:

```properties
sdk.dir=/Users/.../Library/Android/sdk
```

Der Pfad unterscheidet sich von Rechner zu Rechner. Deshalb wird die Datei durch `.gitignore` ausgeschlossen und nicht im Repository gespeichert. Passwörter oder API-Schlüssel sollten ebenfalls nicht in versionierte Gradle-Dateien geschrieben werden.

## Gradle Wrapper und Daemon-JVM

`gradlew` ist das Startskript für macOS und Linux, `gradlew.bat` das entsprechende Windows-Skript. Beide verwenden die in `gradle/wrapper/gradle-wrapper.properties` festgelegte Gradle-Version. Dadurch benötigen alle Beteiligten dieselbe Build-Version, ohne Gradle separat installieren zu müssen.

```bash
./gradlew test
./gradlew assembleDebug
```

`gradle-daemon-jvm.properties` beschreibt die Java-Version für den Gradle-Daemon und enthält plattformspezifische Bezugsquellen. Sie ist nicht mit `compileOptions` zu verwechseln: Die Daemon-JVM führt Gradle aus, während `compileOptions` das Ziel für den Java-Quellcode des Android-Moduls festlegt.

## Aufgabenverteilung im Überblick

| Datei | Leitfrage |
|---|---|
| `settings.gradle.kts` | Welche Module und Repositories gehören zum Build? |
| Root-`build.gradle.kts` | Welche Plugins stehen projektweit bereit? |
| `gradle/libs.versions.toml` | Welche Versionen, Bibliotheken und Plugins sind bekannt? |
| `app/build.gradle.kts` | Wie wird die konkrete Android-App gebaut? |
| `gradle.properties` | Wie arbeitet Gradle in diesem Projekt? |
| `local.properties` | Welche lokalen Rechnerpfade gelten? |
| Gradle Wrapper | Mit welcher Gradle-Version wird gebaut? |

Die nächste Entwicklungsstufe zeigt das Projekt [`gradle_02_shared`](https://github.com/berndRog/gradle_01_wizard/tree/shared). Dort kommt ein Android-Library-Modul hinzu.
