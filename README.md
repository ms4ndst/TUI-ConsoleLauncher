# Original T-UI Linux CLI Launcher

<a href="https://play.google.com/store/apps/details?id=ohi.andre.consolelauncher"><img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" height="60"></a>    <a href="https://f-droid.org/packages/ohi.andre.consolelauncher">
    <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
    alt="Get it on F-Droid"
    height="60">
</a>

## Compatibility

✅ **Android 14+ (API 35) Compatible**
- Updated to Gradle 8.10.2, AGP 8.7.0, Kotlin 2.0.21
- Full AndroidX migration
- Scoped storage support
- Modern security and permission handling

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
./gradlew assembleDebug
```

**Build release APK:**
```bash
./gradlew assembleRelease
```

**Install on connected device:**
```bash
./gradlew installDebug
```

The APK will be generated in:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

### Windows Users
Use `gradlew.bat` instead of `./gradlew`:
```powershell
.\gradlew.bat assembleDebug
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
./gradlew assembleRelease
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
**Please obeserve** this is an unofficial version of the origial project<br>
**Pull requests** are welcome. But **before** you decide to make a major change you should contact the original creator (**[e-mail](mailto:andreuzzi.francesco@gmail.com)**) in order to check if they going to include your change in t-ui, so you don't waste your time.

## How to format a bug report
1. Set "**Bug report**" as subject
2. Describe the issue, when it happens, how to reproduce it
3. **English**! (or Italian, at least)
4. Include any **screenshot** that you think could help (*outputs*, *UI*, etc..)
5. Include any **file** that you think could help (*behavior.xml*, *ui.xml*, etc..)
6. Send it to **andreuzzi.francesco@gmail.com**

## Open source libraries
* [**CompareString2**](https://github.com/fAndreuzzi/CompareString2)
* [**OkHttp**](https://github.com/square/okhttp)
* [**HTML cleaner**](http://htmlcleaner.sourceforge.net/)
* [**JsonPath**](https://github.com/json-path/JsonPath)
* [**jsoup**](https://github.com/jhy/jsoup/)
