package com.example.gptbros.model

import android.content.Context
import androidx.room.Room
import com.example.gptbros.database.GptBrosDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "gpt-bros-database"
class GptBrosRepository private constructor(context: Context) {
    private val coroutineScope : CoroutineScope = GlobalScope
    private val database : GptBrosDatabase = Room
        .databaseBuilder(
            context.applicationContext,  //We give it app context cuz it needs fs
            GptBrosDatabase::class.java,
            DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        //.allowMainThreadQueries()
        //.createFromAsset(DATABASE_NAME) this preloads data from a sql file
        .build()

    // using equals in a function definition is like using a one-liner func def
    fun getSessions(): Flow<List<Session>> = database.gptBrosDao().getSessions()
    fun insertSession(session: Session) = coroutineScope.launch { database.gptBrosDao().insertSession(session) }
    fun insertRecording(recording: Recording) = coroutineScope.launch { database.gptBrosDao().insertRecording(recording) }
    fun insertTranscription(transcription: Transcription) = coroutineScope.launch { database.gptBrosDao().insertTranscription(transcription) }
    fun insertSummary(summary: Summary) = coroutineScope.launch { database.gptBrosDao().insertSummary(summary) }

    fun insertSessionAndChildren(session: Session, recording: Recording, transcription: Transcription, summary: Summary)  {
        //Due to the foreign key constraint we must make sure a recording's corresponding parent
        //session is in the database before inserting
        coroutineScope.launch {
            database.gptBrosDao().insertSession(session)
            database.gptBrosDao().insertRecording(recording)
            database.gptBrosDao().insertTranscription(transcription)
            database.gptBrosDao().insertSummary(summary)
        }
    }

    //fun deleteAllRecord() = database.clearAllTables()
    fun updateSummary(summary: Summary) {
        //all update and insert functions should run in global scope, so the function runs even
        //as user navigates away from fragment
        coroutineScope.launch {
            database.gptBrosDao().updateSummary(summary)
        }
    }
    fun updateSession(session: Session) = coroutineScope.launch { database.gptBrosDao().updateSession(session) }
    fun updateTranscription(transcription: Transcription) = coroutineScope.launch { database.gptBrosDao().updateTranscription(transcription) }
    fun updateRecording(recording: Recording) = coroutineScope.launch { database.gptBrosDao().updateRecording(recording) }


    suspend fun getSessionStatus(sessionVar: Session) : Status{
        //This is essentially just a switch
        return when(sessionVar.stage) {
            Stage.RECORDING -> database.gptBrosDao().getRecording(sessionVar.sessionId).status
            Stage.TRANSCRIBING -> database.gptBrosDao().getTranscription(sessionVar.sessionId).status
            Stage.SUMMARIZING -> database.gptBrosDao().getSummary(sessionVar.sessionId).status
        }
    }
    suspend fun getSummary(sessionId : UUID) = database.gptBrosDao().getSummary(sessionId)
    suspend fun getRecording(sessionId:UUID) = database.gptBrosDao().getRecording(sessionId)
    suspend fun getTranscription(sessionId: UUID) = database.gptBrosDao().getTranscription(sessionId)
    suspend fun getSession(sessionId: UUID) = database.gptBrosDao().getSession(sessionId)
    fun getSummaries() = database.gptBrosDao().getSummaries()

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