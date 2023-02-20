package com.example.gptbros.ui.folder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FolderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Folder Fragment"
    }
    val text: LiveData<String> = _text
}