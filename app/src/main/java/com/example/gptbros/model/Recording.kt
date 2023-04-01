package com.example.gptbros.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Session::class,
        parentColumns = arrayOf("sessionId"),
        childColumns = arrayOf("sessionId"),
        onDelete = ForeignKey.CASCADE)
))
data class Recording(
    @PrimaryKey val recordingId : UUID,
    val sessionId : UUID,
    val fileName : String,
    val className : String,
    val status : Status,
)
