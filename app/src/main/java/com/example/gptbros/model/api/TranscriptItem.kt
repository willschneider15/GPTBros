package com.example.gptbros.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranscriptItem(
    val content: String,
    val confidence: Float,
)