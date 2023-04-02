package com.example.gptbros.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gptbros.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import android.media.AudioManager
import android.os.Build
// import android.support.v7.app.AppCompatActivity

import androidx.annotation.RequiresApi


class HomeFragment : Fragment() {
//    private var output: String? = null
//    private var mediaRecorder: MediaRecorder? = null
//    private var state: Boolean = false
//    private var recordingStopped: Boolean = false

    private lateinit var audioManager: AudioManager

    private var recording = false

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.S)

    // Write a message to the database
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textHome
////        val buttonText: TextView = binding.buttonRecord
////        buttonText.text = "Record"
////
////        val button2 : Button = binding.buttonRecord
////        button2.setOnClickListener{
////            println("clicked button 2")
////            Toast.makeText(view?.context, "Button Clicked", Toast.LENGTH_LONG).show()
////        }
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//
//        }
//        mediaRecorder = context?.let { MediaRecorder(it) }
//        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
//
//        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
//        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//        mediaRecorder?.setOutputFile(output)

//
        binding.buttonRecord.setOnClickListener {
            Toast.makeText(context, "placeholder text", Toast.LENGTH_SHORT)

        }

//        binding.buttonRecord.setOnClickListener{
//            stopRecording()
//        }
//
//        binding.buttonRecord.setOnClickListener {
//            pauseRecording()
//        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.textHome.text="This is an exceptionally hardcoded string"
        auth = FirebaseAuth.getInstance()
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

        }

//        binding.buttonRecord.setOnClickListener {
//            Toast.makeText(view.context, "New exercise will be generated", Toast.LENGTH_SHORT).show()
//            println("Generated a new exercise")
//
//        }




//        binding.buttonRecord.setBackgroundColor(Color.RED)
    }


//    private fun startRecording() {
//        try {
//            mediaRecorder?.prepare()
//            mediaRecorder?.start()
//            state = true
//            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show()
//        } catch (e: IllegalStateException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

//    @SuppressLint("RestrictedApi", "SetTextI18n")
//    @TargetApi(Build.VERSION_CODES.N)
//    private fun pauseRecording() {
//        if(state) {
//            if(!recordingStopped){
//                Toast.makeText(context,"Stopped!", Toast.LENGTH_SHORT).show()
//                mediaRecorder?.pause()
//                recordingStopped = true
//                binding.buttonRecord.text = "Resume"
//            }else{
//                resumeRecording()
//            }
//        }
//    }

//    @SuppressLint("RestrictedApi", "SetTextI18n")
//    @TargetApi(Build.VERSION_CODES.N)
//    private fun resumeRecording() {
//        Toast.makeText(context,"Resume!", Toast.LENGTH_SHORT).show()
//        mediaRecorder?.resume()
//        binding.buttonRecord.text = "Pause"
//        recordingStopped = false
//    }

//    private fun stopRecording(){
//        if(state){
//            mediaRecorder?.stop()
//            mediaRecorder?.release()
//            state = false
//        }else{
//            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun addSession(){
        val audioSession = hashMapOf(
            "sessionId" to 1,
            "audiofile" to "file",
            "transcription" to "trans_file",
            "summary" to "sum_file",
            "dateTimeStart" to "1/1/22-1:22",
            "dateTimeEnd" to "1/1/22-1:56",
            "audiofileURL" to "temp_url"
        )

        // Add a new document with a generated ID
        db.collection("users/"+auth.currentUser?.email.toString())
            .add(audioSession)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}