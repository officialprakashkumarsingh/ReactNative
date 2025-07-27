package com.ahamai.chatapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahamai.chatapp.data.Message
import com.ahamai.chatapp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
        ) {
            if (!message.isFromUser) {
                // AI Avatar
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(iOSSystemGray5),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "AI",
                        tint = iOSSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            // Message Content
            Column(
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(
                        topStart = if (message.isFromUser) 18.dp else 4.dp,
                        topEnd = if (message.isFromUser) 4.dp else 18.dp,
                        bottomStart = 18.dp,
                        bottomEnd = 18.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (message.isFromUser) UserMessageBackground else AssistantMessageBackground
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        SelectionContainer {
                            Text(
                                text = message.content,
                                color = if (message.isFromUser) Color.White else iOSLabel,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = if (message.content.contains("```")) FontFamily.Monospace else FontFamily.Default
                            )
                        }
                        
                        if (message.isStreaming) {
                            Row(
                                modifier = Modifier.padding(top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(12.dp),
                                    strokeWidth = 1.5.dp,
                                    color = if (message.isFromUser) Color.White.copy(alpha = 0.7f) else iOSSystemGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Thinking...",
                                    fontSize = 12.sp,
                                    color = if (message.isFromUser) Color.White.copy(alpha = 0.7f) else iOSSecondaryLabel,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                
                // Timestamp
                Text(
                    text = dateFormatter.format(Date(message.timestamp)),
                    fontSize = 11.sp,
                    color = iOSTertiaryLabel,
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            start = if (message.isFromUser) 0.dp else 12.dp,
                            end = if (message.isFromUser) 12.dp else 0.dp
                        )
                        .align(if (message.isFromUser) Alignment.End else Alignment.Start)
                )
            }
            
            if (message.isFromUser) {
                Spacer(modifier = Modifier.width(8.dp))
                
                // User Avatar
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(UserMessageBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}