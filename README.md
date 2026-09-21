# Gradle-Beispiel: Wizard-Projekt

Dieses Repository zeigt die erste von drei aufeinander aufbauenden Gradle-Stufen. Er enthält ein einzelnes, installierbares Android-Modul `app` und entspricht damit strukturell einem mit Android Studio erzeugten Compose-Projekt.

Die zentrale Beschreibung steht in [docs/Gradle.md](docs/Gradle.md).

## Die drei Stufen

| Projekt | Inhalt | Schwerpunkt |
|---|---|---|
| [`gradle_01_wizard`](https://github.com/berndRog/gradle_01_wizard/tree/master) | ein `app`-Modul | Gradle-Dateien eines Wizard-Projekts |
| [`gradle_02_shared`](https://github.com/berndRog/gradle_01_wizard/tree/shared) | `app` und `Shared` | Application- und Library-Modul |
| [`gradle_03_modules`](https://github.com/berndRog/gradle_01_wizard/tree/modules) | zentrale Konfiguration für `app` und `Shared` | gemeinsame Einstellungen aus Moduldateien herausziehen |

Die drei Projekte lassen sich unabhängig klonen:

```bash
git clone https://github.com/berndRog/gradle_01_wizard.git
git clone https://github.com/berndRog/gradle_02_shared.git
git clone https://github.com/berndRog/gradle_03_modules.git
```

Der Version Catalog enthält bereits Bibliotheken für spätere Vorlesungsbeispiele. Für das Verständnis dieses Projekts ist zunächst nur wichtig, wie Plugins und Bibliotheken über ihre Aliase eingebunden werden.
