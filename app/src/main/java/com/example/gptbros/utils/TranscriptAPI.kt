package com.example.gptbros.utils

import com.example.gptbros.model.api.TranscriptItem
import com.example.gptbros.ui.home.HomeViewModel
import com.google.cloud.speech.v1.RecognitionAudio
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.SpeechClient
import com.google.protobuf.ByteString
import kotlinx.coroutines.delay

// Imports the Google Cloud client library
class TranscriptAPI(
    var byteString: ByteString,
    var speechClient: SpeechClient
) {
    suspend fun fetchTranscript(): TranscriptItem {
        // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
        speechClient.use { speech ->

            // Configure remote file request for FLAC
            val config =
                RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                    .setLanguageCode("en-US")
                    .setSampleRateHertz(16000)
                    .build()
            val audio =
                RecognitionAudio.newBuilder().setUriBytes(
                    byteString
                ).build()

            // Use non-blocking call for getting file transcription
            val response =
                speech.longRunningRecognizeAsync(config, audio)
            while (!response.isDone) {
                println("Waiting for response...")
                delay(10000)
            }
            val results =
                response.get().resultsList

            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            val alternative =
                results[0].alternativesList[0]
            return TranscriptItem(alternative.transcript, alternative.confidence)
        }

    }
}


