package com.ahamai.chatapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahamai.chatapp.data.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    init {
        // Add welcome message
        _messages.add(
            Message(
                content = "Hello! I'm AhamAI, your AI assistant. How can I help you today?",
                isFromUser = false
            )
        )
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        // Add user message
        _messages.add(
            Message(
                content = content.trim(),
                isFromUser = true
            )
        )

        // Simulate AI response
        viewModelScope.launch {
            delay(1000) // Simulate thinking time
            val aiResponse = generateAIResponse(content.trim())
            _messages.add(
                Message(
                    content = aiResponse,
                    isFromUser = false
                )
            )
        }
    }

    fun clearChat() {
        _messages.clear()
        _messages.add(
            Message(
                content = "Chat cleared! How can I help you today?",
                isFromUser = false
            )
        )
    }

    private fun generateAIResponse(userMessage: String): String {
        // Simple response generator for demo purposes
        return when {
            userMessage.lowercase().contains("hello") || userMessage.lowercase().contains("hi") -> {
                "Hello! It's great to meet you. How can I assist you today?"
            }
            userMessage.lowercase().contains("how are you") -> {
                "I'm doing well, thank you for asking! I'm here and ready to help you with any questions or tasks you might have."
            }
            userMessage.lowercase().contains("what can you do") -> {
                "I can help you with a wide variety of tasks including:\n\n• Answering questions\n• Writing and editing text\n• Explaining concepts\n• Problem-solving\n• Creative tasks\n• And much more!\n\nWhat would you like to work on?"
            }
            userMessage.lowercase().contains("bye") || userMessage.lowercase().contains("goodbye") -> {
                "Goodbye! Feel free to come back anytime if you need assistance. Have a great day!"
            }
            userMessage.contains("?") -> {
                "That's an interesting question! While I'm currently in demo mode, I'd be happy to help you explore that topic. In a full implementation, I would provide detailed, helpful responses to your queries."
            }
            else -> {
                "Thank you for your message! I understand you're saying: \"$userMessage\"\n\nI'm currently running in demo mode, but I'm designed to be helpful, harmless, and honest. How else can I assist you today?"
            }
        }
    }
}