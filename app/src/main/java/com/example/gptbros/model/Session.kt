package com.example.gptbros.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Session(
    //TODO: index sessionId inside the other entites
    @PrimaryKey val sessionId : UUID,
    val date : Date,
    val stage : Stage,
)
