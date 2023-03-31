package com.example.gptbros.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class AudioSession(
    @PrimaryKey val id : UUID,
    val audioFileName : String,
    val transcription : String,
    val summary : String,
    val date : Date,
    val userId : UUID,
    val status : String)
