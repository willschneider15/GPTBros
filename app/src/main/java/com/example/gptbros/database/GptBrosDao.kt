package com.example.gptbros.database

import androidx.room.*
import com.example.gptbros.model.Recording
import com.example.gptbros.model.Session
import com.example.gptbros.model.Summary
import com.example.gptbros.model.Transcription
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import java.util.stream.Stream

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

    @Update
    suspend fun updateSession(session : Session)

    @Update
    suspend fun updateSummary(summary: Summary)

    @Update
    suspend fun updateRecording(recording: Recording)

    @Update
    suspend fun updateTranscription(transcription: Transcription)

    //implement relations(?)
    //":sessionId" denotes the sessionId parameter in the method
    @Query("SELECT * FROM summary WHERE sessionId=(:sessionId)")
    suspend fun getSummary(sessionId : UUID) : Summary
    @Query("SELECT * FROM recording WHERE sessionId=(:sessionId)")
    suspend fun getRecording(sessionId : UUID) : Recording
    @Query("SELECT * FROM transcription WHERE sessionId=(:sessionId)")
    suspend fun getTranscription(sessionId : UUID) : Transcription

    @Query("SELECT * FROM session WHERE sessionId=(:sessionId)")
     fun listenOnSession(sessionId : UUID) : Flow<Session>

    @Query("SELECT * FROM summary WHERE sessionId=(:sessionId)")
     fun listOnSummary(sessionId : UUID) : Flow<Summary>
    @Query("SELECT * FROM recording WHERE sessionId=(:sessionId)")
     fun listOnRecording(sessionId : UUID) : Flow<Recording>
    @Query("SELECT * FROM transcription WHERE sessionId=(:sessionId)")
     fun listOnTranscription(sessionId : UUID) : Flow<Transcription>

    @Query("SELECT * FROM session WHERE sessionId=(:sessionId)")
    suspend fun getSession(sessionId : UUID) : Session

    // Add delete methods
    @Delete
    suspend fun deleteSession(session: Session)






    @Query("SELECT * FROM session")
    fun getSessions() : Flow<List<Session>>

    @Query(
        "SELECT * FROM session " +
        "JOIN summary ON session.sessionId = summary.sessionID"
    )
    fun getSummaries() : Flow<Map<Session, Summary>>


}