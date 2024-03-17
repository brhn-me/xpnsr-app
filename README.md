# XPNSR: A Cost Tracking Application

## Overview

XPNSR is a robust cost tracking application designed to simplify the process of managing your
expenses. Leveraging the power of Android Compose alongside a suite of modern development tools,
XPNSR offers an intuitive and efficient way to keep track of your financial transactions.

### Features

- Transaction CRUD Operations: Add, edit, and delete transactions with ease.
- Animated Splash Screen: A visually appealing introduction to the app.
- Real-time Data Handling: Utilize sensors for enhanced functionalities.
- Notifications: Stay updated with periodic reminders and alerts.
- Extensible: Ready for future enhancements like analytical charts and location-based transaction
  mapping.

## Repository

[GitHub Repository](https://github.com/brhn-me/xpnsr-app)

## Technologies Used

- **Programming Languages:** Kotlin, Java
- **Libraries:** Android Compose, Material Design, Room Database, SQLite, Co-Routine, ExoPlayer,
  Coil, Open Street Map
- **Architecture:** Utilizes a BaseActivity class for shared functionality across activities,
  simplifying the codebase and maintaining a consistent UI.

## Source Directory Structure

```
└── xpnsr
    ├── activities
    │   ├── theme
    │   └── TransactionsActivity.kt
    ├── data
    │   ├── converters
    │   ├── dao
    │   ├── db
    │   ├── repository
    │   ├── TransactionsData.kt
    │   └── viewmodels
    ├── models
    ├── services
    ├── utils
    └── workers
```

## UI Concepts

The application's UI/UX design is grounded in Material Design principles, ensuring an
easy-to-navigate interface that enhances user experience.

## Technical Details

- **BaseActivity Class:** Serves as a foundation for all activities, incorporating common features
  such as a sidebar and customizable app bar titles.
- **Database Operations and Live Updates:** Utilizes Room Database for efficient data handling and
  LiveData for real-time UI updates.
- **Notification and Sensor Integration:** Implements background workers for notifications and
  leverages device sensors for additional functionalities.
- **Video Playback and API Calls:** Demonstrates multimedia handling and network operations within
  the app's ecosystem.

## Known Issues

- The camera feature is currently non-functional.
- Sensor live updates are not working after migration from a previous project.

## Possible Future Enhancements

- Integration of chart libraries for financial analysis.
- Map locations based on transaction data.
- UI improvements for better user engagement.

## Getting Started

To run this project, clone the repository and open it in Android Studio. Ensure you have the latest
version of Android Studio and the Android SDK to avoid compatibility issues.
