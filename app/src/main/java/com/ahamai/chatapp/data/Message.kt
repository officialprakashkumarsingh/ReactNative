package com.ahamai.chatapp.data

import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val isStreaming: Boolean = false,
    val isComplete: Boolean = true
)

data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ChatMessage>,
    val stream: Boolean = true,
    val max_tokens: Int = 2000,
    val temperature: Double = 0.7
)

data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatResponse(
    val id: String,
    val choices: List<Choice>,
    val model: String,
    val usage: Usage?
)

data class Choice(
    val delta: Delta?,
    val message: ChatMessage?,
    val finish_reason: String?
)

data class Delta(
    val content: String?
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class ModelsResponse(
    val data: List<Model>
)

data class Model(
    val id: String
)