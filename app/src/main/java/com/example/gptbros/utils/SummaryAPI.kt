package com.example.gptbros.utils

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.*
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.gptbros.BuildConfig
import com.example.gptbros.model.api.SummaryItem


class SummaryAPI (){
    private val openAI = OpenAI(BuildConfig.OPENAI_KEY)
    private var transcript: String = ""


    @OptIn(BetaOpenAI::class)
    fun chatRequest() = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.User,
                content = "Given the following transcript from a lecture, Write a 2-3 sentance general summary of the lecture followed by  " +
                        "bullets that highlight key points. For each point, the point and an " +
                        "explanation of it in: " + transcript
            )
        )
    )

    @OptIn(BetaOpenAI::class)
    fun makeSumItem(chatResponse: ChatCompletion): SummaryItem{
        return SummaryItem(chatResponse.id, chatResponse.model.toString(),
            (chatResponse.choices[0].message?.content ?: String) as String
        )
    }

    @OptIn(BetaOpenAI::class)
    suspend fun fetchSummary(text: String) : SummaryItem {
        transcript = text
        val chatRequest = chatRequest()
        return makeSumItem(openAI.chatCompletion(chatRequest))
    }
}