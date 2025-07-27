package com.ahamai.chatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.ahamai.chatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInput(
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    val sendMessage = {
        if (text.isNotBlank()) {
            onSendMessage(text.trim())
            text = ""
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF343541))
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.chat_hint),
                    color = Color(0xFF8e8ea0)
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = { sendMessage() }
            ),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF40414f),
                focusedTextColor = Color(0xFFececf1),
                unfocusedTextColor = Color(0xFFececf1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF19c37d)
            ),
            maxLines = 4
        )

        FloatingActionButton(
            onClick = sendMessage,
            modifier = Modifier.size(48.dp),
            containerColor = if (text.isNotBlank()) Color(0xFF19c37d) else Color(0xFF565869),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(R.string.send)
            )
        }
    }
}