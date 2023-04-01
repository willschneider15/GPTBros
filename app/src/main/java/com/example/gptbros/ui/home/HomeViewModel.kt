package com.example.gptbros.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Begin the recording at the start of class"
    }
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch {
            //val summary : String =  getSummaries("TRANSCRIPT TEXT")
            val summary = "Summary unimplemented"
            Log.d("HomeViewModel", summary)
        }
    }

}