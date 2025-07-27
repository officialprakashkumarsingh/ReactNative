# AhamAI - ChatGPT UI Clone Android App

A beautiful Android application that replicates the ChatGPT user interface with modern Material Design components and Jetpack Compose.

## Features

- **Modern UI**: Dark theme similar to ChatGPT
- **Chat Interface**: Message bubbles with user and AI distinction
- **Real-time Messaging**: Smooth chat experience with typing animations
- **Material Design 3**: Clean and intuitive interface
- **Jetpack Compose**: Built with modern Android UI toolkit

## Screenshots

The app features:
- Dark theme with professional colors
- Message input field with send button
- Scrollable chat history
- Responsive design for different screen sizes

## App Details

- **Package Name**: com.ahamai.chatapp
- **Version**: 1.0 (1)
- **Min SDK**: 24 (Android 7.0+)
- **Target SDK**: 34 (Android 14)
- **APK Size**: ~15MB

## Installation

1. Download the `AhamAI.apk` file
2. Enable "Install from Unknown Sources" in your Android settings
3. Install the APK file
4. Launch the app and start chatting!

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with ViewModel
- **Material Design**: Material 3 components
- **Build Tool**: Gradle

## Build Instructions

To build the project from source:

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run the project or build APK

```bash
./gradlew assembleDebug
```

The APK will be generated in `app/build/outputs/apk/debug/`

## UI Components

- **ChatScreen**: Main chat interface
- **MessageBubble**: Individual message display
- **ChatInput**: Message input with send button
- **ChatHeader**: Top navigation bar

## Features Implemented

✅ Chat UI with message bubbles  
✅ Dark theme (ChatGPT-like)  
✅ Message input field  
✅ Send button functionality  
✅ Scrollable chat history  
✅ Welcome message  
✅ Simulated AI responses  

## Note

This is a UI clone for demonstration purposes. It includes simulated AI responses for testing the interface. To integrate with a real AI service, you would need to add API integration in the `ChatViewModel`.

---

**Created for demonstrating modern Android development with Jetpack Compose and Material Design 3.**
