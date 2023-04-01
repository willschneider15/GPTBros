package com.example.gptbros.model

import android.content.Context
import androidx.room.Room
import com.example.gptbros.database.GptBrosDatabase
import kotlinx.coroutines.flow.Flow
import java.util.*

private const val DATABASE_NAME = "gpt-bros-database"
class GptBrosRepository private constructor(context: Context) {
    private val database : GptBrosDatabase = Room
        .databaseBuilder(
            context.applicationContext,  //We give it app context cuz it needs fs
            GptBrosDatabase::class.java,
            DATABASE_NAME
        )
        //.allowMainThreadQueries()
        //.createFromAsset(DATABASE_NAME) this preloads data from a sql file
        .build()

    // using equals in a function definition is like using a one-liner func def
    suspend fun getSessions(): Flow<List<Session>> = database.gptBrosDao().getSessions()
    suspend fun insertSession(session: Session) = database.gptBrosDao().insertSession(session)
    suspend fun insertRecording(recording: Recording) = database.gptBrosDao().insertRecording(recording)
    suspend fun insertTranscription(transcription: Transcription) = database.gptBrosDao().insertTranscription(transcription)
    suspend fun insertSummary(summary: Summary) = database.gptBrosDao().insertSummary(summary)
    suspend fun getSummary(sessionId : UUID) = database.gptBrosDao().getSummary(sessionId)
    suspend fun deleteAllRecord() = database.clearAllTables()

    companion object {
        // "CrimeRepository?" Just says that it's allowed to be null
        private var INSTANCE: GptBrosRepository? = null


        //Only calls init if instance is null
        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = GptBrosRepository(context)
            }
        }

        fun get(): GptBrosRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initalized")
        }
    }
}