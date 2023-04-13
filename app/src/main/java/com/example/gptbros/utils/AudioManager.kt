package com.example.gptbros.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.gptbros.ui.MainActivity
import java.io.IOException


class AudioManager() {

    private lateinit var mediaRecorder: MediaRecorder
    private var mediaPlayer: MediaPlayer? = null
    private var fileLocation: String = ""
    private var output: String? = null
    private var TAG = "AudioManager"
    var isRecording = false


    fun startRecording(recordingName : String, recordingCategory: String, context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                mediaRecorder = MediaRecorder(context)
            } else {
                //use older constructor if needed THIS IS NOT TESTED
                mediaRecorder = MediaRecorder()
            }
            val cw : ContextWrapper = ContextWrapper(context)
            output = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                cw.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/"+ recordingName + ".webm"
            } else {
                Environment.getExternalStorageDirectory().absolutePath + "/"+ recordingName + ".webm"
            }
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(AudioFormat.ENCODING_PCM_16BIT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.setOutputFile(output)
            Log.d(TAG,output!!.toString())
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording = true
        }catch (e : java.lang.IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder.stop()
        isRecording = false
        mediaRecorder.release()
    }
}