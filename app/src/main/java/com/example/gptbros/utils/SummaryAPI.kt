package com.example.gptbros.utils

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.*
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.flow.Flow

const val API_KEY = "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"

class SummaryAPI {
    private val openAI = OpenAI(API_KEY)
    private var transcript: String = ""
        get() {
            return field
        }
        set(value) { field = value }

    @OptIn(BetaOpenAI::class)
    fun chatRequest() = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.User,
                content = "Given the following transcript from a lecture, Create a summary of the transcript " +
                        "that highlights key points and gives insight on the main concepts: " + transcript
            )
        )
    )
    @OptIn(BetaOpenAI::class)
    suspend fun fetchSummary(text: String): ChatCompletion {
        transcript = text
        val chatRequest = chatRequest()
        return openAI.chatCompletion(chatRequest)
    }
}