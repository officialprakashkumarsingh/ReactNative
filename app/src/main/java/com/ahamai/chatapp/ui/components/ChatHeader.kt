package com.ahamai.chatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ahamai.chatapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    selectedModel: String = "gpt-3.5-turbo",
    availableModels: List<String> = listOf("gpt-3.5-turbo", "gpt-4", "claude-3-sonnet"),
    onModelSelected: (String) -> Unit = {},
    onClearChat: () -> Unit = {},
    onRetry: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var showDropdownMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = iOSBackground,
        shadowElevation = 0.5.dp
    ) {
        Column {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "AhamAI",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = iOSLabel
                        )
                        Text(
                            text = selectedModel,
                            style = MaterialTheme.typography.labelMedium,
                            color = iOSSecondaryLabel
                        )
                    }
                },
                actions = {
                    // Retry button (when needed)
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp),
                            strokeWidth = 2.dp,
                            color = iOSSystemBlue
                        )
                    } else {
                        IconButton(
                            onClick = onRetry
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Retry",
                                tint = iOSSystemBlue
                            )
                        }
                    }
                    
                    // More options menu
                    Box {
                        IconButton(
                            onClick = { showDropdownMenu = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = iOSSystemBlue
                            )
                        }
                        
                        DropdownMenu(
                            expanded = showDropdownMenu,
                            onDismissRequest = { showDropdownMenu = false }
                        ) {
                            // Model selection
                            Text(
                                text = "Select Model",
                                style = MaterialTheme.typography.labelLarge,
                                color = iOSSecondaryLabel,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                            
                            availableModels.forEach { model ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = model,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = iOSLabel
                                            )
                                            if (model == selectedModel) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Selected",
                                                    tint = iOSSystemBlue,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        onModelSelected(model)
                                        showDropdownMenu = false
                                    }
                                )
                            }
                            
                            Divider(
                                color = iOSSystemGray5,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            
                            // Clear chat option
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Clear Chat",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = ErrorColor
                                    )
                                },
                                onClick = {
                                    onClearChat()
                                    showDropdownMenu = false
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = iOSBackground,
                    titleContentColor = iOSLabel,
                    actionIconContentColor = iOSSystemBlue
                )
            )
            
            // Bottom divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(iOSSystemGray4)
            )
        }
    }
}

@Composable
fun StatusIndicator(
    isConnected: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = when {
                        isLoading -> WarningColor
                        isConnected -> SuccessColor
                        else -> ErrorColor
                    },
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = when {
                isLoading -> "Thinking..."
                isConnected -> "Online"
                else -> "Offline"
            },
            style = MaterialTheme.typography.labelSmall,
            color = iOSSecondaryLabel
        )
    }
}