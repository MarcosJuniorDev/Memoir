# Memoir

[Português (Brasil)](README.pt-BR.md)

Memoir is a Java desktop application for organizing games and keeping local backups of save files. The core idea is simple: register a game, point Memoir to its save folder, choose where backups should be stored, and let the app handle copying, restoring, and change detection.

The project uses Swing with FlatLaf for the interface, Maven for builds, Gson for JSON persistence, and custom services for backup, restore, save hashing, and process monitoring.

## Features

- Game registration with name, executable, save folder, and backup folder.
- Visual library with game cards and covers.
- Manual save-file backups.
- Restore saved backups back to the original save folder.
- Hash-based change detection to avoid repeated backups when nothing changed.
- Last backup date tracking.
- Comments and rating per game.
- Optional auto backup after the game process exits.
- Optional SteamGridDB integration to fetch covers automatically.
- System tray icon to keep the app accessible.

## How it works

Each registered game stores the following information:

- game name;
- executable name or path used for process detection;
- folder where the game stores save files;
- folder where Memoir should store the backup;
- cover image path;
- hash of the last backup;
- last backup date;
- comment, rating, and auto-backup state.

When a backup runs, Memoir calculates the hash of the save folder. If the current hash is different from the last saved hash, it copies the save folder to the configured backup folder. If nothing changed, the backup is skipped.

When restoring, Memoir copies the files from the backup folder back to the game's save folder.

## Requirements

- JDK 25 or later.
- Maven 3.9 or later.
- Desktop environment with Swing support.

The `pom.xml` is configured with Java `source` and `target` set to 25. If you are using another JDK version, adjust the `maven-compiler-plugin` before building.

## Build

From the project root:

```bash
mvn clean package
```

Maven generates an executable JAR with dependencies at:

```text
target/memoir-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Running

After building:

```bash
java -jar target/memoir-1.0-SNAPSHOT-jar-with-dependencies.jar
```

You can also run it from the IDE using the main class:

```text
main.App
```

## SteamGridDB configuration

Automatic cover search uses the SteamGridDB API. To enable this integration, create the following file:

```text
src/main/resources/config.properties
```

With this content:

```properties
steamgrid.api.key=YOUR_KEY_HERE
```

This file is ignored by Git and must not be committed, because it contains a private API key.

If this file does not exist, Memoir still works, but automatic SteamGridDB cover search will not have a valid authentication key.

## Local data

Game data is saved in a `gameData.json` file inside the user's data directory, resolved by the `appdirs` library.

On Linux, the path usually follows this pattern:

```text
~/.local/share/Memoir/gameData.json
```

Covers downloaded through the integration are also stored in the user's data directory, inside a `covers` folder.

The `gameData.json` file in the project root is ignored by Git and should not be used as the main production data source.

## Project structure

```text
src/main/App.java                         Application entry point
src/main/model/Game.java                  Model with each game's data
src/main/service/BackupService.java       Backup, restore, and backup removal
src/main/service/GameRepository.java      Local JSON persistence
src/main/service/HashFileSave.java        Save-file hash calculation
src/main/service/AutoBackupService.java   Game process monitoring
src/main/service/SteamGridService.java    SteamGridDB integration
src/main/ui/                              Swing screens and components
src/main/resources/                       Icons, fonts, and manifest
```

## Main dependencies

- Gson: JSON serialization and parsing.
- Apache Commons IO: directory copy and removal.
- MigLayout: Swing layout management.
- FlatLaf: modern look and feel for the interface.
- Dorkbox SystemTray: system tray integration.
- AppDirs: user data directory resolution by operating system.

## Basic usage flow

1. Open Memoir.
2. Click `ADD GAME`.
3. Enter the game name, executable, save folder, and backup folder.
4. Optionally select or search for a cover.
5. Save the registration.
6. Use `Backup` to create a copy of the saves.
7. Use `Restore` to restore the backup when needed.
8. Enable `Auto backup` if you want Memoir to monitor the game process and back up saves when it exits.

## Development notes

- The application prevents multiple instances by using a local socket on port `63971`.
- Backup folder names are sanitized to avoid invalid characters on Windows.
- Auto backup searches for the configured executable in the process command or command line, which helps with Linux, Proton, and Wine scenarios.
- The build uses `maven-assembly-plugin` to package a single JAR with dependencies.

## License

Memoir is licensed under the GNU General Public License v3.0. See [LICENSE](LICENSE) for details.

Third-party dependencies keep their own licenses.

## Roadmap

- Cloud backup.
- Save versioning.
