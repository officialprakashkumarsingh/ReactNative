package com.ahamai.chatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahamai.chatapp.ui.components.ChatHeader
import com.ahamai.chatapp.ui.components.ChatInput
import com.ahamai.chatapp.ui.components.MessageBubble
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    val messages = viewModel.messages
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Auto scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            ChatHeader(
                onClearChat = viewModel::clearChat
            )
        },
        bottomBar = {
            ChatInput(
                onSendMessage = viewModel::sendMessage
            )
        },
        containerColor = Color(0xFF343541),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF343541))
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = messages,
                key = { message -> message.id }
            ) { message ->
                MessageBubble(message = message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}