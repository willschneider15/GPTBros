package com.example.gptbros.database

import androidx.room.Dao
import androidx.room.Query
import com.example.gptbros.model.AudioSession
import kotlinx.coroutines.flow.Flow

@Dao
interface GptBrosDao {

    @Query("Select * from AudioSession")
    fun getTranscripts() : Flow<List<AudioSession>>
}