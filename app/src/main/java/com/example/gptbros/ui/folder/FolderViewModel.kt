package com.example.gptbros.ui.folder

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptbros.adapters.DatabaseTupleAdapter
import com.example.gptbros.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log


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
            gptBrosRepository.getSessions().collect() {
                val result : MutableList<FolderListItem> = mutableListOf()
                Log.d("FOLDERVIEWMODEL",  "id="+it.toString())
                it.forEach { entry ->
                    val status: Status =  gptBrosRepository.getSessionStatus(entry)
                    result.add(
                        DatabaseTupleAdapter
                            .convertSessionSummaryToFolderListItem(entry,
                            status
                            )
                    )
                }
                _folderListItems.value = result.toList()
            }
        }


        Log.d("FOLDERVIEWMODEL", "FOLDER VIEW MODEL WAS INSTANTIATED")
    }

    suspend fun populateDB() {
        val sessions : List<Session> = listOf(
            Session(UUID.randomUUID(), Date(), Stage.SUMMARIZING, "CSE 5246", "POSIX THREADS"),
            Session(UUID.randomUUID(), Date(), Stage.RECORDING, "APP DEV", "Intro mobile languages"),
            Session(UUID.randomUUID(), Date(), Stage.TRANSCRIBING, "APP DEV", "Intro mobile languages")
        )
        val summaries : List<Summary> = listOf(
            Summary(UUID.randomUUID(), sessions[0].sessionId, Status.IN_PROGRESS, "Summary1"),
        )
        val recordings : List<Recording> = listOf(
            Recording(UUID.randomUUID(), sessions[1].sessionId, Status.ERROR),
        )
        val transcriptions : List<Transcription> = listOf(
            Transcription(UUID.randomUUID(), sessions[2].sessionId, Status.FINISHED,"Transcription1")
        )

        sessions.forEach{
            gptBrosRepository.insertSession(it)
        }
        summaries.forEach {
            gptBrosRepository.insertSummary(it)
        }
        transcriptions.forEach {
            gptBrosRepository.insertTranscription(it)
        }
        recordings.forEach{
            gptBrosRepository.insertRecording(it)
        }
    }
}