# Gradle Example: Wizard Project

[Deutsche Version](README_ger.md)

This repository shows the first of three successive Gradle stages. It contains a single installable Android module named `app` and therefore structurally corresponds to a Compose project created with the Android Studio wizard.

The detailed explanation is available in [docs/Gradle.md](docs/Gradle.md). A [German version](docs/Gradle_ger.md) is also available.

## The three stages

| Project | Contents | Focus |
|---|---|---|
| [`gradle_01_wizard`](https://github.com/berndRog/gradle_01_wizard) | one `app` module | Gradle files of a wizard project |
| [`gradle_02_shared`](https://github.com/berndRog/gradle_02_shared) | `app` and `Shared` | application and library modules |
| [`gradle_03_modules`](https://github.com/berndRog/gradle_03_modules) | centralized configuration for `app` and `Shared` | extracting common settings from module build files |

The three projects can be cloned independently:

```bash
git clone https://github.com/berndRog/gradle_01_wizard.git
git clone https://github.com/berndRog/gradle_02_shared.git
git clone https://github.com/berndRog/gradle_03_modules.git
```

The version catalog already contains libraries used in later course examples. At this stage, the important point is how plugins and libraries are included through their aliases.
