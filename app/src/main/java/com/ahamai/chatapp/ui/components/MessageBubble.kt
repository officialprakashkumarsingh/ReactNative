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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.ahamai.chatapp.data.Message
import com.ahamai.chatapp.ui.theme.*
import io.noties.markwon.Markwon
import io.noties.markwon.syntax.Prism4jTheme
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

@PrismBundle(includeAll = true)
@Composable
fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            if (message.isFromUser) {
                // User message - right aligned, blue bubble
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 6.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = UserMessageBackground
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 1.dp
                            )
                        ) {
                            SelectionContainer {
                                Text(
                                    text = message.content,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }
                        
                        Text(
                            text = dateFormatter.format(Date(message.timestamp)),
                            style = MaterialTheme.typography.labelSmall,
                            color = iOSSecondaryLabel,
                            modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                        )
                    }
                }
            } else {
                // AI message - left aligned, gray bubble with markdown
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 6.dp,
                                bottomEnd = 20.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = AssistantMessageBackground
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 1.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                if (message.content.isNotEmpty()) {
                                    // Render markdown content
                                    MarkdownText(
                                        text = message.content,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                
                                // Show typing indicator for streaming messages
                                if (message.isStreaming && message.content.isEmpty()) {
                                    TypingIndicator()
                                }
                            }
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = "AI",
                                tint = iOSSystemBlue,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = dateFormatter.format(Date(message.timestamp)),
                                style = MaterialTheme.typography.labelSmall,
                                color = iOSSecondaryLabel
                            )
                            if (message.isStreaming) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = iOSSystemBlue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarkdownText(
    text: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    val markwon = remember {
        Markwon.builder(context)
            .usePlugin(
                SyntaxHighlightPlugin.create(
                    Prism4j(),
                    Prism4jTheme.builder()
                        .background(0xFF2D2D2D)
                        .textColor(0xFFE1E1E1)
                        .punctuation(0xFF8E8E93)
                        .property(0xFF569CD6)
                        .keyword(0xFFD73A49)
                        .tag(0xFF22863A)
                        .string(0xFF032F62)
                        .number(0xFF005CC5)
                        .comment(0xFF6A737D)
                        .className(0xFF6F42C1)
                        .function(0xFF6F42C1)
                        .build()
                )
            )
            .build()
    }

    SelectionContainer {
        AndroidView(
            modifier = modifier,
            factory = { ctx ->
                TextView(ctx).apply {
                    textSize = 17f
                    setTextColor(context.getColor(android.R.color.black))
                    setPadding(0, 0, 0, 0)
                }
            },
            update = { textView ->
                markwon.setMarkdown(textView, text)
            }
        )
    }
}

@Composable
fun TypingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        repeat(3) { index ->
            val alpha by animateFloatAsState(
                targetValue = if ((LocalTime.current.nano / 500_000_000 + index) % 3 == 0) 0.3f else 1f,
                animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                    androidx.compose.animation.core.tween(500),
                    androidx.compose.animation.core.RepeatMode.Reverse
                ),
                label = "typing_dot_$index"
            )
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(iOSSystemGray.copy(alpha = alpha))
            )
            
            if (index < 2) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

object LocalTime {
    val current: java.time.LocalTime
        @Composable get() = remember {
            var time by mutableStateOf(java.time.LocalTime.now())
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(100)
                time = java.time.LocalTime.now()
            }
            time
        }
}