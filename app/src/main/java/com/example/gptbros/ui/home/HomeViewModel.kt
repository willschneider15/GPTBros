package com.example.gptbros.ui.home


import com.google.cloud.speech.v1.SpeechClient
import com.google.cloud.speech.v1.SpeechSettings
import com.google.auth.Credentials
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.gax.core.FixedCredentialsProvider


import com.google.cloud.speech.v1.RecognitionAudio
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.RecognizeRequest
import com.google.protobuf.ByteString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

import java.io.FileInputStream
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptbros.R
import com.example.gptbros.model.*
import com.example.gptbros.model.api.SummaryItem
import com.example.gptbros.utils.AudioManager
import com.example.gptbros.utils.SummaryAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.Date
import java.util.UUID


import java.io.ByteArrayOutputStream
import java.io.FileOutputStream



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

    fun summarizeRecording(context: Context) {
        //Look into using something other than global scope as this is bad practice for api calls
        //Also look into transactions so process destruction doesn't corrupt db
        GlobalScope.launch {
            val transcription : Transcription = gptBrosRepository.getTranscription(sessionUUID)
            val session : Session = gptBrosRepository.getSession(sessionUUID)
            val summary : Summary = gptBrosRepository.getSummary(sessionUUID)
            gptBrosRepository.updateTranscription(transcription.copy(status = Status.IN_PROGRESS))

            val cw : ContextWrapper = ContextWrapper(context)
            val filePath : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                cw.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/"+ session.label + ".wav"
            } else {
                Environment.getExternalStorageDirectory().absolutePath + "/"+ session.label + ".wav"
            }

            val transcriptionRes  = transcribeRecording(context,filePath)

            //put result in transcribe.copy(content=)
            gptBrosRepository.updateTranscription(transcription.copy(content = transcriptionRes, status = Status.FINISHED))
            gptBrosRepository.updateSession(session.copy(stage = Stage.SUMMARIZING))

            //put result in summary.copy
            val summaryRes = summarizeTranscription(transcriptionRes)
            Log.d(ContentValues.TAG, "Summary response: " + summaryRes)
            gptBrosRepository.updateSummary(summary.copy(content = summaryRes, status = Status.FINISHED))
        }
    }

    private fun createSpeechClient(context: Context): SpeechClient {
        val settingsBuilder = SpeechSettings.newBuilder()
        val credentialsStream: InputStream = context.resources.openRawResource(R.raw.credential)

        val credentials = GoogleCredentials.fromStream(credentialsStream)
        settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials))

        return SpeechClient.create(settingsBuilder.build())
    }
    suspend fun transcribeRecording(context: Context, filePath: String): String {
        return withContext(Dispatchers.IO) {
            val speechClient = createSpeechClient(context)
            val data = (ByteString.copyFrom(File(filePath).readBytes()))
            Log.d("TUT", "ByteString data size: ${data.size()}")
            val config = RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setLanguageCode("en-US")
                        .setSampleRateHertz(16000)
                        .build()


            val audio = RecognitionAudio.newBuilder()
                .setContent(data)
                .build()


            val response = speechClient.recognize(config, audio)

            Log.d("TUT", "Response, count ${response.resultsCount}")
            val results = response.resultsList
            for (result in results) {
                val alternative = result.getAlternativesList().get(0)
                val text = alternative.getTranscript()
                Log.d("TUT", "Transcription: $text")
            }

            val transcript = response.resultsList.joinToString(" ") { it.alternativesList[0].transcript }
            Log.d(ContentValues.TAG, "Transcrip response: " + transcript)
            speechClient.shutdown()
            transcript
        }
    }





    suspend fun summarizeTranscription(transcription : String) : String {
        val summaryAPI = SummaryAPI()
        lateinit var summaryItem: SummaryItem
        try {
            summaryItem = summaryAPI.fetchSummary(transcription)
            Log.d(ContentValues.TAG, "Summary response: " + summaryItem.content)
        } catch (e: java.lang.Exception) {
            Log.e(ContentValues.TAG, "Failed to fetch summary api response", e)
        }
        return summaryItem.content
    }






}