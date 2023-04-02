package com.example.gptbros.ui.folder

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptbros.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*


class FolderViewModel : ViewModel() {

    private val TEST_DATA : List<FolderListItem> = listOf(
        FolderListItem(UUID.randomUUID(), "Introduction; characteristics of mobile applications", "CSE 5236", Stage.TRANSCRIBING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Overview of mobile application development languages (Swift and Java)", "CSE 5236", Stage.SUMMARIZING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Managing mobile application data", "CSE 5236", Stage.RECORDING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Fourth item", "CSE 5236", Stage.RECORDING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Integrating cloud services with mobile applications", "CSE 5236", Stage.SUMMARIZING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Integrating networking, the OS, and hardware into mobile applications", "CSE 5236", Stage.TRANSCRIBING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Middle Item", "CSE 5236", Stage.SUMMARIZING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "POSIX Threads Concepts", "Parallel Computing", Stage.TRANSCRIBING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "OpenMP Programming Model Concepts", "Parallel Computing", Stage.SUMMARIZING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "GPU Computing Concepts", "Parallel Computing", Stage.TRANSCRIBING, Status.IN_PROGRESS, Date()),
        FolderListItem(UUID.randomUUID(), "Basic Message Passing Interface (MPI) Concepts", "Parallel Computing", Stage.TRANSCRIBING, Status.FINISHED, Date()),
        FolderListItem(UUID.randomUUID(), "Finals Review", "Parallel Computing", Stage.SUMMARIZING, Status.ERROR, Date()),
        FolderListItem(UUID.randomUUID(), "Cool Class", "Parallel Computing", Stage.TRANSCRIBING, Status.FINISHED, Date()),
        FolderListItem(UUID.randomUUID(), "Fun Class", "Parallel Computing", Stage.SUMMARIZING, Status.ERROR, Date()),
    )

    private val gptBrosRepository = GptBrosRepository.get()
    private val _folderListItems : MutableStateFlow<List<FolderListItem>> = MutableStateFlow(emptyList())
    val folderListItems : StateFlow<List<FolderListItem>>
        get() = _folderListItems.asStateFlow()



    private val _text = MutableLiveData<String>().apply {
        value = "This is Folder Fragment"
    }
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch {
            _folderListItems.value = TEST_DATA
            //Whenever our flow has a new value we collect it and put into our MutableStateFlow
//            val session : Session = Session(UUID.randomUUID(),Date(), Stage.RECORDING, false)
//            val inputSessions : List<Session> = gptBrosRepository.getSessions()
//            val summary : Summary = Summary(
//                UUID.randomUUID(),
//                inputSessions[0].sessionId,
//                Status.NOT_STARTED,
//                "Test"
//            )
//            gptBrosRepository.insertSummary(summary)
            //gptBrosRepository.deleteAllRecord()
            //delete and update functionality
//            Log.d("FOLDERVIEWMODEL",  "id="+inputSessions[0].sessionId+"actual="+gptBrosRepository.getSummary(inputSessions[0].sessionId).sessionId.toString())

        }
        Log.d("FOLDERVIEWMODEL", "FOLDER VIEW MODEL WAS INSTANTIATED")
    }
}