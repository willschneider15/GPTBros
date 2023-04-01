package com.example.gptbros.model

import java.util.Date
import java.util.UUID

data class FolderListItem(
    val sessionId : UUID,
    val SummaryName : String,
    val className : String,
    val Stage: Stage,
    val Status: Status,
    val date: Date,
)
