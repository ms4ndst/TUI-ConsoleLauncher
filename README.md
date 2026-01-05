# Original T-UI Linux CLI Launcher

<a href="https://f-droid.org/packages/ohi.andre.consolelauncher">
    <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
    alt="Get it on F-Droid"
    height="60">
</a>

## Current Version
- **v6.18** (`versionCode` **208**)

## Compatibility
- **minSdk: 21**
- **targetSdk: 35** (Android 15)
- Gradle **8.10.2**, AGP **8.7.0**, Kotlin **2.0.21**
- AndroidX

## Recent Updates (January 2026)

### Themes (local palette)
- `theme -view` now lists built-in themes **alphabetically and numbered**.
- `theme -apply <number>` applies the selected theme from that list and **reloads** the UI.
- `theme -viewe` is kept as an alias for `theme -view`.
- `theme -apply <string>` still supports the legacy online theme ID/name behavior.

### System info UI tweaks
- RAM line shows additional device info:
  - `Android: <release> (API <sdk>)` (yellow)
  - `Build: <Build.DISPLAY>` (pink)
- Unlock information now uses the **same color as the Internal Storage label**.

### Network & Weather Improvements
- **Wi-Fi Status Display**: Fixed Wi-Fi detection using NetworkCapabilities API with legacy fallback for older devices. Now correctly shows connected Wi-Fi SSID instead of "no internet connection".
- **Weather Command (`tuiweather`)**: Enhanced weather output format
  - Now displays: `[Location]: [Condition] [Temperature]°C`
  - Example: `Stockholm: Clear 5.99°C`
  - Switched to HTTPS for OpenWeatherMap API calls (Android cleartext policy compliance)
  - Improved internet connectivity checks with fail-open logic
- **Format Migration**: Existing installations automatically migrate to new weather display format.

### Build System
- Module build output is moved outside the repo on Windows to avoid OneDrive file lock issues:
  - `%LOCALAPPDATA%\TUI-ConsoleLauncher-build\app`
- If `./gradlew clean` fails due to locked files, build without it (e.g. `assembleRelease` / `installRelease`).

## Build Instructions

### Prerequisites
- **JDK 17** or higher
- **Android SDK** with Android 15 (API 35)
- **Gradle 8.10.2** (included via wrapper)

### Building from Source

**Clone the repository:**
```bash
git clone https://github.com/ms4ndst/TUI-ConsoleLauncher.git
cd TUI-ConsoleLauncher
```

**Build debug APK:**
```bash
./gradlew :app:assembleDebug
```

**Build release APK:**
```bash
./gradlew :app:assembleRelease
```

**Install on connected device:**
```bash
./gradlew :app:installDebug
```

**Install release on connected device:**
```bash
./gradlew :app:installRelease
```

The APK will be generated under Gradle's module build directory:
- On Windows (default in this repo): `%LOCALAPPDATA%\TUI-ConsoleLauncher-build\app\outputs\apk\...`
- Otherwise: `app/build/outputs/apk/...` (or the configured `buildDir`)

### Windows Users
Use `gradlew.bat` instead of `./gradlew`:
```powershell
.\gradlew.bat :app:assembleDebug
```

### Release Signing

The project is configured with release signing using a keystore. The configuration is stored in `keystore.properties`:

```properties
storePassword=android123
keyPassword=android123
keyAlias=tui-launcher
storeFile=../release.keystore
```

**For Development:**
The included `release.keystore` uses default credentials for convenience. Release builds are automatically signed when you run:
```bash
./gradlew :app:assembleRelease
```

**For Production:**
Generate a new keystore with strong credentials:
```bash
keytool -genkeypair -v -keystore release.keystore -alias tui-launcher \
  -keyalg RSA -keysize 2048 -validity 10000
```

Then update `keystore.properties` with your credentials. **Never commit production keystores or credentials to version control.**

**Security Notes:**
- The `.gitignore` is configured to exclude `*.keystore` and `*.jks` files
- Keep production keystores in a secure location outside the repository
- For CI/CD, use encrypted secrets or secure credential storage

## Official links from the original project by  **[fandreuz](mailto:andreuzzi.francesco@gmail.com)**

**@tui_launcher**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[Twitter.com](https://twitter.com/tui_launcher)**<br>
**Official community**&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[Reddit](https://www.reddit.com/r/tui_launcher/)**<br>
**Official Group**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[Telegram](https://t.me/tuilauncher)**<br>
**GitHub**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[GitHub.com](https://github.com/Andre1299/TUI-ConsoleLauncher)**<br>
**Wiki**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[GitHub.com](https://github.com/Andre1299/TUI-ConsoleLauncher/wiki)**<br>
**FAQ**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[GitHub.com](https://github.com/Andre1299/TUI-ConsoleLauncher/wiki/FAQ)**

## Some mentions

- **[XDA](https://www.xda-developers.com/linux-cli-launcher-transforms-your-home-screen-into-a-terminal/)**
- **[Android Authority](http://www.androidauthority.com/linux-cli-launcher-turns-homepage-linux-command-line-interface-767431/)**
- **[Gadget Hacks](https://android.gadgethacks.com/how-to/linux-style-launcher-turns-your-home-screen-into-command-prompt-0177326/)**

## Contributing
**Please obeserve** this is an unofficial version of the origial project<p>
**Pull requests** are welcome. But **before** you decide to make a major change you should contact the original creator (**[e-mail](mailto:andreuzzi.francesco@gmail.com)**) in order to check if they going to include your change in t-ui, so you don't waste your time.


## Open source libraries
* [**CompareString2**](https://github.com/fAndreuzzi/CompareString2)
* [**OkHttp**](https://github.com/square/okhttp)
* [**HTML cleaner**](http://htmlcleaner.sourceforge.net/)
* [**JsonPath**](https://github.com/json-path/JsonPath)
* [**jsoup**](https://github.com/jhy/jsoup/)
