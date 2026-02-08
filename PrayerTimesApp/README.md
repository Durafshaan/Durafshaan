# Prayer Times Android App

A comprehensive native Android application for managing Islamic prayer times, azan times, and Islamic calendar events.

## Features

### 🕌 Prayer Times Management
- **Display Prayer Times**: Shows all five daily prayers (Fajr, Dhuhr, Asr, Maghrib, Isha)
- **Manual Time Setting**: Ability to manually set both prayer times and azan times
- **Auto Calculation**: Automatic prayer time calculation based on location and calculation method
- **Multiple Calculation Methods**: Support for various Islamic calculation methods (ISNA, MWL, Egyptian, Makkah, Karachi, Tehran)

### 🔕 Silent Mode Automation
- **Automatic Silent Mode**: Automatically silences phone 10 minutes before each prayer time
- **Customizable Duration**: Adjustable silent mode duration (1-60 minutes before prayer)
- **Smart Restoration**: Automatically restores normal mode after prayer time

### 📅 Islamic Calendar
- **Event Management**: Add, view, and manage Islamic events and important dates
- **Pre-loaded Events**: Comes with major Islamic events (Eid al-Fitr, Eid al-Adha, Laylat al-Qadr, etc.)
- **Event Types**: Categorized events (Eid, Ramadan, Hajj, Friday Prayer, etc.)
- **Recurring Events**: Support for yearly recurring Islamic events

### 🔔 Notifications & Alerts
- **Prayer Notifications**: Timely notifications for each prayer
- **Azan Notifications**: Separate notifications for azan times
- **Customizable Sounds**: Choose different azan sounds and adjust volume
- **Vibration Support**: Optional vibration alerts

### ⚙️ Settings & Customization
- **Location Settings**: Set latitude and longitude for accurate prayer times
- **Notification Preferences**: Enable/disable various notification types
- **Sound Settings**: Customize azan sounds and volume levels
- **Silent Mode Settings**: Configure automatic silent mode behavior

## Technical Architecture

### 🏗️ Architecture Components
- **MVVM Pattern**: Model-View-ViewModel architecture
- **Room Database**: Local data persistence for prayer times, events, and settings
- **LiveData & Flow**: Reactive data observation
- **WorkManager**: Background task scheduling for notifications and silent mode
- **AlarmManager**: Precise timing for prayer alerts

### 📱 UI/UX
- **Material Design 3**: Modern Material You design system
- **Bottom Navigation**: Easy navigation between main sections
- **Responsive Layout**: Optimized for various screen sizes
- **Dark/Light Theme**: Automatic theme support

### 🔧 Key Components

#### Data Layer
- **Room Database**: SQLite database with DAOs for each entity
- **Models**: PrayerTime, AzanTime, IslamicEvent, PrayerSettings
- **Type Converters**: For complex data types and enums

#### Business Logic
- **PrayerTimeCalculator**: Astronomical calculations for prayer times
- **SilentModeManager**: Handles automatic phone silencing
- **PermissionHelper**: Manages required permissions

#### UI Layer
- **MainActivity**: Main container with bottom navigation
- **HomeFragment**: Dashboard with today's prayer times and events
- **TimesFragment**: Manual time setting interface
- **CalendarFragment**: Islamic calendar and event management
- **SettingsFragment**: App configuration and preferences

## Permissions Required

- `ACCESS_NOTIFICATION_POLICY`: For Do Not Disturb access
- `MODIFY_AUDIO_SETTINGS`: For silent mode control
- `SCHEDULE_EXACT_ALARM`: For precise prayer time alarms
- `POST_NOTIFICATIONS`: For prayer time notifications
- `RECEIVE_BOOT_COMPLETED`: For alarm restoration after device restart

## Installation & Setup

1. **Clone the repository**
2. **Open in Android Studio**
3. **Build and run** on Android device (API 24+)
4. **Grant permissions** when prompted
5. **Set location** in settings for automatic prayer time calculation

## Usage

### First Time Setup
1. Open the app and grant required permissions
2. Go to Settings and enter your location (latitude/longitude)
3. Choose your preferred calculation method
4. Configure notification and silent mode preferences

### Daily Use
- **Home Screen**: View today's prayer times and upcoming prayer
- **Times Tab**: Manually adjust prayer and azan times if needed
- **Calendar Tab**: View Islamic events and add custom events
- **Settings Tab**: Modify app behavior and preferences

### Adding Custom Events
1. Go to Calendar tab
2. Tap the floating action button (+)
3. Enter event details (title, description, date, type)
4. Mark as important or recurring if needed

## Calculation Methods

The app supports multiple Islamic calculation methods:

- **ISNA**: Islamic Society of North America
- **MWL**: Muslim World League
- **Egyptian**: Egyptian General Authority of Survey
- **Makkah**: Umm Al-Qura University, Makkah
- **Karachi**: University of Islamic Sciences, Karachi
- **Tehran**: Institute of Geophysics, University of Tehran

## Technical Requirements

- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Language**: Kotlin
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (SQLite)
- **UI**: Material Design 3

## Key Features Implementation

### Silent Mode Automation
- Uses AlarmManager for precise timing
- Fallback to WorkManager for newer Android versions
- Respects Do Not Disturb permissions
- Automatically restores normal mode

### Prayer Time Calculation
- Astronomical calculations based on sun position
- Multiple calculation methods support
- Location-based accuracy
- Manual override capability

### Notification System
- Separate channels for different notification types
- Customizable sounds and vibration
- High priority for prayer time alerts
- Background service for reliability

## Future Enhancements

- **Qibla Direction**: Compass pointing to Mecca
- **Hijri Calendar**: Full Hijri date support
- **Prayer Tracking**: Track completed prayers
- **Mosque Finder**: Nearby mosque locations
- **Tasbih Counter**: Digital prayer bead counter
- **Quran Integration**: Verses and recitation
- **Multiple Languages**: Localization support

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For issues, feature requests, or questions, please create an issue in the repository.

---

**May Allah accept our prayers and make this app beneficial for the Muslim community. Ameen.**
