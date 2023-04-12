package com.example.gptbros.ui.home

import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.example.gptbros.model.api.TranscriptItem
import com.example.gptbros.utils.SummaryAPI
import com.example.gptbros.utils.TranscriptAPI
import com.google.cloud.speech.v1.SpeechClient
import com.google.protobuf.ByteString
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@OptIn(BetaOpenAI::class)
class HomeViewModel : ViewModel() {

    lateinit var transcriptItem : TranscriptItem

    init {
    }

    fun callAPIs(speechClient: SpeechClient?) {
        viewModelScope.launch {
            var filename = "short_audio_file.mp3"
            val data: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val path: Path = Paths.get(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ filename)
                Files.readAllBytes(path)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val audioBytes: ByteString = ByteString.copyFromUtf8(data.toUByteArray().toString())
            Log.d(ContentValues.TAG, "ByteString Created " + audioBytes.isEmpty)

            var transcriptApi: TranscriptAPI? = speechClient?.let { TranscriptAPI(audioBytes, it) }
            transcriptItem = transcriptApi!!.fetchTranscript()
            Log.d(ContentValues.TAG, "Transcript API response received, Transcript Confidence:" + transcriptItem.confidence);

            try {
                val summaryAPI = SummaryAPI()
                val summaryItem = summaryAPI.fetchSummary(transcriptItem.content)
                Log.d(ContentValues.TAG,"API response received, Summary text: " + summaryItem.content)
            } catch (e: java.lang.Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch summary api response", e)
            }

        }

    }



}