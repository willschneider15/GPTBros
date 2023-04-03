package com.example.gptbros.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.gptbros.model.Session
import com.example.gptbros.model.Status
import java.util.UUID

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Session::class,
        parentColumns = arrayOf("sessionId"),
        childColumns = arrayOf("sessionId"),
        onDelete = ForeignKey.CASCADE)
))
data class Transcription(
    @PrimaryKey val transcriptionId : UUID,
    val sessionId : UUID,
    val status : Status,
    val content : String
)
