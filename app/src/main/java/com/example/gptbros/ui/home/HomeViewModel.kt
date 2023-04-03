package com.example.gptbros.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.example.gptbros.model.api.SummaryItem
import com.example.gptbros.model.api.TranscriptItem
import com.example.gptbros.utils.SummaryAPI
import com.example.gptbros.utils.TranscriptAPI
import com.google.cloud.speech.v1.SpeechClient
import com.google.protobuf.ByteString
import kotlinx.coroutines.launch
import java.io.File


@OptIn(BetaOpenAI::class)
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Begin the recording at the start of class"
    }
    val text: LiveData<String> = _text
    val _recFilePath = MutableLiveData<String>().apply {
        value = ""
    }
    val recFilePath: LiveData<String> = _recFilePath
    val _speechClient = MutableLiveData<SpeechClient>().apply {
        value = SpeechClient.create()
    }
    val speechClient: LiveData<SpeechClient> = _speechClient
    val summaryAPI = SummaryAPI()
    private val transcriptAPI = TranscriptAPI()
    var transcript: String = ""

    init {
        viewModelScope.launch {
            try {
                val filePath: String? = recFilePath.value
                val sc: SpeechClient? = speechClient.value
                val transcriptItem: TranscriptItem? = sc?.let {
                    transcriptAPI.fetchTranscript(ByteString.copyFrom(File(filePath).readBytes()),
                        it
                    )
                }
                transcript = transcriptItem?.content.toString()
                Log.d(ContentValues.TAG, "Transcript API response received, Transcript Confidence: ${transcriptItem?.confidence}")
            } catch (ex: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch transcript api response", ex)
            }
            try {
                val summary: SummaryItem = summaryAPI.fetchSummary(transcript)
                Log.d(TAG, "API response received, Summary text: ${summary.content}")
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch summary api response", ex)
            }
        }
    }

}