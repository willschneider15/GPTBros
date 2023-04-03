package com.example.gptbros.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GPTResponse(
    val chatResponse: SummaryItem
)