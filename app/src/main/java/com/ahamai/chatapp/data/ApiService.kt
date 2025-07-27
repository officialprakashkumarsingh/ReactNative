package com.ahamai.chatapp.data

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiService {
    private val baseUrl = "https://ahamai-api.officialprakashkrsingh.workers.dev"
    private val apiKey = "ahamaibyprakash25"
    private val gson = Gson()
    
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    suspend fun streamChatCompletion(messages: List<ChatMessage>): Flow<String> = flow {
        val request = ChatRequest(
            messages = messages,
            stream = true
        )
        
        val requestBody = gson.toJson(request).toRequestBody()
        
        val httpRequest = Request.Builder()
            .url("$baseUrl/v1/chat/completions")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        val response = suspendCancellableCoroutine<Response> { continuation ->
            val call = client.newCall(httpRequest)
            continuation.invokeOnCancellation { call.cancel() }
            
            try {
                val response = call.execute()
                continuation.resume(response)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }

        if (!response.isSuccessful) {
            throw Exception("API call failed: ${response.code}")
        }

        val responseBody = response.body ?: throw Exception("Empty response body")
        
        try {
            val reader = BufferedReader(InputStreamReader(responseBody.byteStream()))
            var line: String?
            
            while (reader.readLine().also { line = it } != null) {
                if (line!!.startsWith("data: ")) {
                    val data = line!!.substring(6).trim()
                    
                    if (data == "[DONE]") {
                        break
                    }
                    
                    if (data.isNotEmpty()) {
                        try {
                            val chatResponse = gson.fromJson(data, ChatResponse::class.java)
                            val content = chatResponse.choices.firstOrNull()?.delta?.content
                            if (!content.isNullOrEmpty()) {
                                emit(content)
                            }
                        } catch (e: JsonSyntaxException) {
                            // Skip malformed JSON
                            continue
                        }
                    }
                }
            }
        } finally {
            responseBody.close()
        }
    }

    suspend fun getAvailableModels(): List<String> {
        // Return default models for now
        return listOf("gpt-3.5-turbo", "gpt-4", "claude-3-sonnet")
    }
}