package com.example.gptbros.utils

import com.example.gptbros.model.api.TranscriptItem
import com.google.cloud.speech.v1.RecognitionAudio
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.RecognizeRequest
import com.google.cloud.speech.v1.SpeechClient
import com.google.protobuf.ByteString

class TranscriptAPI {
    fun fetchTranscript(fileByteString: ByteString, speechClient: SpeechClient): TranscriptItem {
        val req = RecognizeRequest.newBuilder()
            .setConfig(
                RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.AMR_WB)
                    .setLanguageCode("en-US")
                    .setSampleRateHertz(16000)
                    .build()
            )
            .setAudio(
                RecognitionAudio.newBuilder()
                    .setContent(fileByteString)
                    .build()
            )
            .build()
        val response = speechClient.recognize(req)

        val results = response.resultsList
        val alternative = results[0].alternativesList[0]

        return TranscriptItem(alternative.transcript, alternative.confidence)
    }

}


