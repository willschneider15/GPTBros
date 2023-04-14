package com.example.gptbros.utils

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream

class AudioManager() {
    private var audioRecord: AudioRecord? = null
    public var isRecording = false
    private val TAG = "AudioManager"

    fun startRecording(recordingName: String, context: Context) {
        val bufferSize = AudioRecord.getMinBufferSize(
            16000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            16000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        val audioData = ByteArray(bufferSize)

        val cw: ContextWrapper = ContextWrapper(context)
        val output = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            cw.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/" + recordingName + ".wav"
        } else {
            Environment.getExternalStorageDirectory().absolutePath + "/" + recordingName + ".wav"
        }

        val outputFile = File(output)
        if (outputFile.exists()) {
            outputFile.delete()
        }

        val outputStream = FileOutputStream(outputFile)
        val bufferedStream = BufferedOutputStream(outputStream)
        val dataOutputStream = DataOutputStream(bufferedStream)

        audioRecord?.startRecording()
        isRecording = true

        Thread {
            while (isRecording) {
                val bytesRead = audioRecord?.read(audioData, 0, bufferSize) ?: 0
                if (bytesRead > 0) {
                    dataOutputStream.write(audioData, 0, bytesRead)
                }
            }

            dataOutputStream.flush()
            dataOutputStream.close()
            bufferedStream.close()
            outputStream.close()

            Log.d(TAG, "Recording saved: $output")
        }.start()
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}