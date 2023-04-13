package com.example.gptbros.ui.home

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gptbros.databinding.FragmentHomeBinding
import com.example.gptbros.ui.MainActivity


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.gptbros.R
import com.example.gptbros.utils.AudioManager


import java.io.IOException

class HomeFragment : Fragment() {
    private var TAG :String= "HOME FRAGMENT"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by viewModels()
    private var audioManager : AudioManager? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.buttonRecord.setOnClickListener {
            //If the version is wrong just do nothing, in the future display some message
                if(!checkPermissions() ) {
                    askForPermissions() //assume they say yes
                    return@setOnClickListener
                }
                if(audioManager?.isRecording == false) {
                    val name = binding.recordingName.text.toString()
                    val category = binding.recordingCatagory.text.toString()
                    audioManager?.startRecording(name, category,activity!!)
                    homeViewModel.onRecordUpdateDb(name, category)
                    //this function can only be called when the ui in on screen (activity !=  null)
                    //binding.buttonRecord.background (activity!!.getColor(R.color.red))
                    Log.d(TAG, "Start recording")
                } else if(audioManager?.isRecording == true){
                    //Open diologue for name + group
                    //Call the approiate vm methods
                    audioManager?.stopRecording()
                    homeViewModel.onStopRecordUpdateDb()
                    homeViewModel.summarizeRecording(requireContext())
                    Log.d(TAG, "Stop recording")
                    //binding.buttonRecord.setBackgroundColor(activity!!.getColor(R.color.white))
                }


        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioManager = AudioManager()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        if(audioManager?.isRecording == true) {
            audioManager?.stopRecording()
        }
        //local variables from fragments don't disappear when you navigate away..
        _binding = null
        audioManager = null
    }

    fun askForPermissions() {
        //we know context will be non null because this function is only called when view is on screen
        if(context != null && activity != null && activity is MainActivity) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity!!, permissions,0)
        } else {
            Log.e(TAG, "Something went wrong when asking for permissions!")
        }
    }

    fun checkPermissions() : Boolean {
        if(context != null && activity != null && activity is MainActivity) {
            val audioPermission : Boolean = ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            val writePermission : Boolean = ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            return  audioPermission && writePermission
        }
        return false
    }
}