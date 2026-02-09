# Towards Islam - Islamic Prayer Times Android App

A comprehensive **Native Android Application** built with **Kotlin** and **MVVM Architecture** that provides complete Islamic prayer features. The app follows **Material Design 3**, is lightweight, battery efficient, and works offline after data is saved.

## 🌟 Project Overview

This is a production-ready Islamic prayer times application built using the latest Android development standards, supporting **Android 16 (API Level 35)** and using **Android Gradle Plugin (AGP) 9.0.0** with the latest stable dependencies.

### Key Features

- **Prayer Times**: Display daily prayer times (Fajr, Sunrise, Dhuhr, Asr, Maghrib, Isha)
- **Azan Playback**: Play Azan audio at prayer times with volume control
- **Auto Silent Mode**: Automatically enable silent mode before prayers
- **Hijri Calendar**: Islamic calendar with important dates and events
- **Qibla Direction**: Compass-based Qibla finder using device sensors
- **Notifications**: Prayer time and reminder notifications
- **Offline Support**: Works offline after initial data sync
- **Material Design 3**: Modern UI with dark/light theme support
- **Battery Optimized**: Efficient background processing

## 🏗️ Technical Architecture

### Build Configuration
- **Language**: Kotlin 2.0.21
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose with Material Design 3
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 35 (Android 16)
- **Compile SDK**: API 35
- **Android Gradle Plugin**: 9.0.0
- **Gradle Version**: 8.9

### Core Technologies
- **Database**: Room Database for offline storage
- **Networking**: Retrofit + OkHttp for API calls
- **Dependency Injection**: Hilt
- **Background Processing**: WorkManager
- **Reactive Programming**: Kotlin Coroutines + Flow
- **Navigation**: Navigation Component
- **Media**: ExoPlayer for audio playback
- **Permissions**: Accompanist for permission handling

### API Integration
- **Aladhan Prayer Times API**: For accurate prayer times
- **Aladhan Hijri Calendar API**: For Islamic calendar data

## 📂 Project Structure

```
TowardsIslam/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/towardsislam/app/
│       │   ├── TowardsIslamApplication.kt
│       │   ├── presentation/
│       │   │   ├── MainActivity.kt
│       │   │   ├── navigation/
│       │   │   ├── screen/
│       │   │   │   ├── home/
│       │   │   │   ├── calendar/
│       │   │   │   ├── qibla/
│       │   │   │   └── settings/
│       │   │   └── theme/
│       │   └── worker/
│       └── res/
│           ├── drawable/
│           ├── values/
│           └── raw/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradle/
    ├── libs.versions.toml
    └── wrapper/
```

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Ladybug | 2024.2.1 or later
- **JDK**: 17 or later
- **Android SDK**: API 35

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd TowardsIslam
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the TowardsIslam directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio

## 📱 Features Implementation Status

### ✅ Completed
- Project structure and build configuration
- Material Design 3 theming with Islamic green color palette
- Navigation system with 4 main screens
- Application class with notification channels
- Splash screen with mosque icon
- Basic UI screens (Home, Calendar, Qibla, Settings)
- Gradle configuration with latest dependencies
- AndroidManifest with all required permissions

### 🚧 In Progress / TODO
- Complete prayer times functionality with API integration
- Room database implementation for offline storage
- Azan audio playback service
- Auto silent mode with WorkManager
- Hijri calendar with important Islamic dates
- Qibla compass with sensor integration
- Notification system implementation
- Settings screen with user preferences
- Location-based prayer time calculation
- Background services and broadcast receivers

## 🔧 Development Setup

### Dependencies Management
The project uses Gradle Version Catalog (`libs.versions.toml`) for centralized dependency management:

- **Core Android**: androidx.core-ktx, appcompat, activity-compose
- **Jetpack Compose**: BOM 2024.12.01, Material3, Animation
- **Architecture**: Lifecycle, Navigation, Room, WorkManager
- **Networking**: Retrofit 2.11.0, OkHttp 4.12.0
- **DI**: Hilt 2.51.1
- **Coroutines**: 1.9.0
- **Media**: ExoPlayer 2.19.1

### Build Variants
- **Debug**: Development build with logging
- **Release**: Optimized build with ProGuard/R8

## 🎨 Design System

### Color Palette
- **Primary**: Islamic Green (#009357)
- **Secondary**: Violet accent colors
- **Surface**: Neutral colors with proper contrast
- **Error**: Red variants for error states

### Typography
- Material Design 3 typography scale
- Support for Arabic text rendering
- Proper text sizing for accessibility

## 🔔 Permissions

The app requires the following permissions:
- `INTERNET` - API calls for prayer times
- `ACCESS_FINE_LOCATION` - GPS-based location
- `POST_NOTIFICATIONS` - Prayer notifications (Android 13+)
- `SCHEDULE_EXACT_ALARM` - Precise prayer time alarms
- `MODIFY_AUDIO_SETTINGS` - Silent mode functionality
- `FOREGROUND_SERVICE` - Background processing

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## 📦 Build & Release

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- **Aladhan API** for Islamic prayer times data
- **Material Design 3** for modern UI components
- **Android Jetpack** for architecture components
- **Islamic Community** for guidance on prayer calculations

---

**Built with ❤️ for the Muslim community**

*This project demonstrates modern Android development practices with clean architecture, offline-first approach, and user-centric design.*

