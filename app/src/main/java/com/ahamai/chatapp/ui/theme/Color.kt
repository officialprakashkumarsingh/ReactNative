package com.ahamai.chatapp.ui.theme

import androidx.compose.ui.graphics.Color

// iOS-inspired minimalistic colors
val iOSSystemBlue = Color(0xFF007AFF)
val iOSSystemGray = Color(0xFF8E8E93)
val iOSSystemGray2 = Color(0xFFAEAEB2)
val iOSSystemGray3 = Color(0xFFC7C7CC)
val iOSSystemGray4 = Color(0xFFD1D1D6)
val iOSSystemGray5 = Color(0xFFE5E5EA)
val iOSSystemGray6 = Color(0xFFF2F2F7)

// Background colors
val iOSBackground = Color(0xFFFFFFFF)
val iOSSecondaryBackground = Color(0xFFF2F2F7)
val iOSGroupedBackground = Color(0xFFF2F2F7)

// Text colors
val iOSLabel = Color(0xFF000000)
val iOSSecondaryLabel = Color(0xFF3C3C43).copy(alpha = 0.6f)
val iOSTertiaryLabel = Color(0xFF3C3C43).copy(alpha = 0.3f)

// Chat specific colors
val UserMessageBackground = iOSSystemBlue
val AssistantMessageBackground = iOSSystemGray6
val MessageInputBackground = Color(0xFFFFFFFF)
val MessageInputBorder = iOSSystemGray4

// Accent colors
val AccentColor = iOSSystemBlue
val SuccessColor = Color(0xFF34C759)
val ErrorColor = Color(0xFFFF3B30)
val WarningColor = Color(0xFFFF9500)

// Dark mode colors (for future use)
val iOSBackgroundDark = Color(0xFF000000)
val iOSSecondaryBackgroundDark = Color(0xFF1C1C1E)
val iOSLabelDark = Color(0xFFFFFFFF)