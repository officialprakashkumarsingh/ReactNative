package com.ahamai.chatapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahamai.chatapp.data.ApiService
import com.ahamai.chatapp.data.ChatMessage
import com.ahamai.chatapp.data.Message
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion

class ChatViewModel : ViewModel() {
    private val apiService = ApiService()
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    var isLoading by mutableStateOf(false)
        private set
    
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var selectedModel by mutableStateOf("gpt-3.5-turbo")
        private set

    var availableModels by mutableStateOf(listOf<String>())

    init {
        // Add welcome message
        _messages.add(
            Message(
                content = "Hello! I'm AhamAI, your AI assistant powered by advanced language models. How can I help you today?",
                isFromUser = false
            )
        )

        // Fetch available models
        viewModelScope.launch {
            availableModels = try {
                apiService.getAvailableModels()
            } catch (_: Exception) {
                listOf("gpt-3.5-turbo")
            }
            selectedModel = availableModels.firstOrNull() ?: selectedModel
        }
    }

    fun sendMessage(content: String) {
        if (content.isBlank() || isLoading) return

        // Clear any previous error
        errorMessage = null

        // Add user message
        val userMessage = Message(
            content = content.trim(),
            isFromUser = true
        )
        _messages.add(userMessage)

        // Create AI message placeholder for streaming
        val aiMessage = Message(
            content = "",
            isFromUser = false,
            isStreaming = true,
            isComplete = false
        )
        _messages.add(aiMessage)

        isLoading = true

        viewModelScope.launch {
            try {
                // Convert messages to API format
                val apiMessages = _messages
                    .filter { it.isComplete } // Only include completed messages
                    .map { message ->
                        ChatMessage(
                            role = if (message.isFromUser) "user" else "assistant",
                            content = message.content
                        )
                    }

                var accumulatedContent = ""
                val aiMessageIndex = _messages.size - 1

                apiService.streamChatCompletion(selectedModel, apiMessages)
                    .catch { exception ->
                        errorMessage = "Failed to get response: ${exception.message}"
                        // Remove the incomplete AI message
                        _messages.removeAt(aiMessageIndex)
                    }
                    .onCompletion {
                        isLoading = false
                        // Mark the AI message as complete
                        if (aiMessageIndex < _messages.size) {
                            val completedMessage = _messages[aiMessageIndex].copy(
                                isStreaming = false,
                                isComplete = true
                            )
                            _messages[aiMessageIndex] = completedMessage
                        }
                    }
                    .collect { chunk ->
                        accumulatedContent += chunk
                        // Update the AI message with accumulated content
                        if (aiMessageIndex < _messages.size) {
                            val updatedMessage = _messages[aiMessageIndex].copy(
                                content = accumulatedContent
                            )
                            _messages[aiMessageIndex] = updatedMessage
                        }
                    }
            } catch (exception: Exception) {
                isLoading = false
                errorMessage = "Failed to send message: ${exception.message}"
                // Remove the incomplete AI message
                if (_messages.isNotEmpty() && !_messages.last().isFromUser) {
                    _messages.removeAt(_messages.size - 1)
                }
            }
        }
    }

    fun clearChat() {
        _messages.clear()
        errorMessage = null
        _messages.add(
            Message(
                content = "Chat cleared! How can I help you today?",
                isFromUser = false
            )
        )
    }

    fun selectModel(model: String) {
        selectedModel = model
    }

    fun dismissError() {
        errorMessage = null
    }

    fun retryLastMessage() {
        if (_messages.isNotEmpty()) {
            val lastUserMessage = _messages.findLast { it.isFromUser }
            lastUserMessage?.let { message ->
                // Remove any incomplete AI messages
                while (_messages.isNotEmpty() && !_messages.last().isFromUser && !_messages.last().isComplete) {
                    _messages.removeAt(_messages.size - 1)
                }
                sendMessage(message.content)
            }
        }
    }
}