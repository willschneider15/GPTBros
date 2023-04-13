package com.example.gptbros.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SummaryItem(
    val id: String,
    val model: String,
    val content: String,
)