package com.example.gptbros.ui.home

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptbros.model.*
import com.example.gptbros.utils.AudioManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class HomeViewModel : ViewModel() {
    private val gptBrosRepository = GptBrosRepository.get()
    private val _text = MutableLiveData<String>().apply {
        value = "Begin the recording at the start of class"
    }
    private lateinit var sessionUUID : UUID
    val text: LiveData<String> = _text
    val isRecording = false

    init {
        viewModelScope.launch {
            //val summary : String =  getSummaries("TRANSCRIPT TEXT")
            val summary = "Summary unimplemented"
            Log.d("HomeViewModel", summary)
        }
    }

    fun onRecordUpdateDb(recordingName : String, recordingCategory : String) {
        val session : Session = Session(UUID.randomUUID(), Date(), Stage.RECORDING, recordingCategory, recordingName)
        sessionUUID = session.sessionId
        val recording: Recording = Recording(UUID.randomUUID(), session.sessionId, Status.IN_PROGRESS)
        val transcription : Transcription = Transcription(UUID.randomUUID(), session.sessionId, Status.NOT_STARTED, "Recording not transcribed yet")
        val summary : Summary = Summary(UUID.randomUUID(), session.sessionId, Status.NOT_STARTED, "Summary not transcribed yet")
        gptBrosRepository.insertSessionAndChildren(session, recording, transcription, summary)
    }

    fun onStopRecordUpdateDb() {
        viewModelScope.launch {
            val session : Session = gptBrosRepository.getSession(sessionUUID)
            val recording: Recording = gptBrosRepository.getRecording(sessionUUID)
            gptBrosRepository.updateSession(session.copy(stage = Stage.TRANSCRIBING))
            gptBrosRepository.updateRecording(recording.copy(status = Status.FINISHED))
        }
    }

    fun summarizeRecording() {
        //Look into using something other than global scope as this is bad practice for api calls
        //Also look into transactions so process destruction doesn't corrupt db
        GlobalScope.launch {
            val transcription : Transcription = gptBrosRepository.getTranscription(sessionUUID)
            val session : Session = gptBrosRepository.getSession(sessionUUID)
            val summary : Summary = gptBrosRepository.getSummary(sessionUUID)
            gptBrosRepository.updateTranscription(transcription.copy(status = Status.IN_PROGRESS))

            val filePath : String = Environment.getExternalStorageDirectory().absolutePath +
                     "/"+ session.label + ".acc"

            val transcriptionRes  = transcribeRecording(filePath)

            //put result in transcribe.copy(content=)
            gptBrosRepository.updateTranscription(transcription.copy(content = transcriptionRes, status = Status.FINISHED))
            gptBrosRepository.updateSession(session.copy(stage = Stage.SUMMARIZING))

            //put result in summary.copy
            val summaryRes = summarizeTranscription(transcriptionRes)
            gptBrosRepository.updateSummary(summary.copy(content = summaryRes, status = Status.FINISHED))
        }
    }
    suspend fun transcribeRecording(filePath : String) : String {
        return "test"
    }

    suspend fun summarizeTranscription(transcription : String) : String {
        return "test"
    }






}