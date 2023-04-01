package com.example.gptbros.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gptbros.model.Recording
import com.example.gptbros.model.Session
import com.example.gptbros.model.Summary
import com.example.gptbros.model.Transcription
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface GptBrosDao {
    @Insert
    suspend fun insertSession(session: Session)
    @Insert
    suspend fun insertRecording(recording: Recording)
    @Insert
    suspend fun insertSummary(summary: Summary)
    @Insert
    suspend fun insertTranscription(transcription: Transcription)

    //implement relations(?)
    //":sessionId" denotes the sessionId parameter in the method
    @Query("SELECT * FROM summary WHERE sessionId=(:sessionId)")
    suspend fun getSummary(sessionId : UUID) : Summary
    @Query("SELECT * FROM session")
    fun getSessions() : Flow<List<Session>>

    @Query(
        "SELECT * FROM session " +
        "JOIN summary ON session.sessionId"
    )
    fun getSummaries() : Flow<Map<Session, List<Summary>>>


}