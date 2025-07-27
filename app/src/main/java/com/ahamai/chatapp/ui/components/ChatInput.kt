package com.ahamai.chatapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.ahamai.chatapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatInput(
    onSendMessage: (String) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val sendMessage = {
        if (text.isNotBlank() && !isLoading) {
            onSendMessage(text)
            text = ""
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = iOSBackground,
        shadowElevation = 1.dp
    ) {
        Column {
            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(iOSSystemGray4)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                // Text input
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 1.dp,
                            color = if (text.isNotEmpty()) iOSSystemBlue else iOSSystemGray4,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(MessageInputBackground)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = iOSLabel
                            ),
                            cursorBrush = SolidColor(iOSSystemBlue),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Send
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = { sendMessage() }
                            ),
                            maxLines = 5,
                            decorationBox = { innerTextField ->
                                if (text.isEmpty()) {
                                    Text(
                                        text = "Message",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = iOSSecondaryLabel
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Send button
                IconButton(
                    onClick = sendMessage,
                    enabled = text.isNotBlank() && !isLoading,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            if (text.isNotBlank() && !isLoading) iOSSystemBlue 
                            else iOSSystemGray4
                        )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModelSelector(
    selectedModel: String,
    availableModels: List<String>,
    onModelSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedModel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Model") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = iOSSystemBlue,
                unfocusedBorderColor = iOSSystemGray3,
                focusedLabelColor = iOSSystemBlue,
                unfocusedLabelColor = iOSSystemGray
            )
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableModels.forEach { model ->
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = model,
                            style = MaterialTheme.typography.bodyMedium
                        ) 
                    },
                    onClick = {
                        onModelSelected(model)
                        expanded = false
                    },
                    leadingIcon = if (model == selectedModel) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = iOSSystemBlue
                            )
                        }
                    } else null
                )
            }
        }
    }
}