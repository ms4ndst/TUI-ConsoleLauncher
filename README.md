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

## Recent Updates (January 2026)

### Network & Weather Improvements
- **Wi-Fi Status Display**: Fixed Wi-Fi detection using NetworkCapabilities API with legacy fallback for older devices. Now correctly shows connected Wi-Fi SSID instead of "no internet connection"

- **Weather command (`tuiweather`)**
  - Enable/disable the weather label:
    - `tuiweather -enable` - Show weather in status bar
    - `tuiweather -disable` - Hide weather from status bar
  - Set location:
    - `tuiweather -location <value>` - Set weather location
      - **Coordinates required**: `tuiweather -location 59.33,18.06` (lat,lon for Stockholm)
      - Automatically reverse-geocodes coordinates to display city name
      - Falls back to showing coordinates if geocoding is unavailable
  - Manual refresh:
    - `tuiweather -update` - Force immediate weather update
  - Automatic updates:
    - `tuiweather -auto true` - Enable periodic weather updates (default)
    - `tuiweather -auto false` - Disable periodic updates (manual only)
  - Tutorial/Documentation:
    - `tuiweather -tutorial` - Opens SMHI weather data information page
  
  **Weather Data Source:**
  - Uses **SMHI** (Swedish Meteorological Institute) - Official Swedish weather service
  - Free API with no authentication required
  - Accurate forecasts for Sweden and surrounding areas
  - API endpoint: `opendata-download-metfcst.smhi.se/api/category/pmp3g`
  - Requires coordinates in `lat,lon` format
  - Configure location via command or in `behavior.xml`: `weather_location`
  
  **Display Format:**
  - Default format: `%name: %main %temp°C`
  - Format variables:
    - `%name` - Location name (auto-geocoded from coordinates, e.g., "Stockholm")
    - `%main` - Weather condition (e.g., "Clear sky", "Overcast")
    - `%temp` - Temperature in Celsius
  - Example output: `Stockholm: Overcast -0.8°C`
  - Configure via `behavior.xml`: `weather_format`
  
  **Location Detection:**
  - Automatically converts coordinates to city names using Android Geocoder
  - Tries: Locality (city) → Sub-admin area (county) → Admin area (state/province)
  - Falls back to coordinates if geocoding fails or is unavailable
  - Requires network access for geocoding functionality
  
  **Technical Details:**
  - HTTPS API calls for security compliance
  - SMHI forecast JSON parsing with JsonPath
  - Weather symbol codes (1-27) automatically converted to readable text
  - Configurable auto-update interval via `weather_update_time` (seconds)
  - Update control via `weather_auto_update` boolean setting

### Build System
- Custom build directory (`.build`) to avoid OneDrive sync conflicts
- Updated ProGuard rules for R8 compatibility with explicit constructor patterns

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
**Please obeserve** this is an unofficial version of the origial project<p>
**Pull requests** are welcome. But **before** you decide to make a major change you should contact the original creator (**[e-mail](mailto:andreuzzi.francesco@gmail.com)**) in order to check if they going to include your change in t-ui, so you don't waste your time.


## Open source libraries
* [**CompareString2**](https://github.com/fAndreuzzi/CompareString2)
* [**OkHttp**](https://github.com/square/okhttp)
* [**HTML cleaner**](http://htmlcleaner.sourceforge.net/)
* [**JsonPath**](https://github.com/json-path/JsonPath)
* [**jsoup**](https://github.com/jhy/jsoup/)
