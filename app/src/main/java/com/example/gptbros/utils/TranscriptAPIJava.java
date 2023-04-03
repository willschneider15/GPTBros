package com.example.gptbros.utils;
// Imports the Google Cloud client library
import com.example.gptbros.model.api.TranscriptItem;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import java.util.List;

public class TranscriptAPIJava {

    /**
     * Demonstrates using the Speech API to transcribe an audio file.
     *
     * @return
     */
    public TranscriptItem fetchTranscript() throws Exception {
//        // Instantiates a client
//        try (SpeechClient speechClient = SpeechClient.create()) {
//
//            // The path to the audio file to transcribe
//            String gcsUri = "sampledata/Baker Systems Engineering.m4a";
//
//            // Builds the sync recognize request
//            RecognitionConfig config =
//                    RecognitionConfig.newBuilder()
//                            .setEncoding(AudioEncoding.LINEAR16)
//                            .setSampleRateHertz(16000)
//                            .setLanguageCode("en-US")
//                            .build();
//            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();
//
//            // Performs speech recognition on the audio file
//            RecognizeResponse response = speechClient.recognize(config, audio);
//            List<SpeechRecognitionResult> results = response.getResultsList();
//
//            SpeechRecognitionAlternative alternative = null;
//
//            for (SpeechRecognitionResult result : results) {
//                // There can be several alternative transcripts for a given chunk of speech. Just use the
//                // first (most likely) one here.
//                alternative = result.getAlternativesList().get(0);
//            }
//            return new TranscriptItem(alternative.getTranscript(), alternative.getConfidence());
//        }

        return null;
    }
}
            

