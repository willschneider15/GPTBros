package com.example.gptbros.ui.folder

import android.util.Log
import androidx.lifecycle.*
import com.example.gptbros.adapters.DatabaseTupleAdapter
import com.example.gptbros.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*


class AccountViewModel(sessionId : UUID) : ViewModel() {
    private val TAG : String = "AccountViewModel"
    private val gptBrosRepository = GptBrosRepository.get()
    private val _session : MutableStateFlow<Session> = MutableStateFlow(Session(UUID.randomUUID(), Date(), Stage.RECORDING, "Loading...", "Loading..."))
    val session : StateFlow<Session>
        get() = _session.asStateFlow()

    private val _recording : MutableStateFlow<Recording> = MutableStateFlow(Recording(UUID.randomUUID(), UUID.randomUUID(), Status.NOT_STARTED))
    val recording : StateFlow<Recording>
        get() = _recording.asStateFlow()

    private val _transcription : MutableStateFlow<Transcription> = MutableStateFlow(Transcription(UUID.randomUUID(), UUID.randomUUID(), Status.IN_PROGRESS, "Loading..."))
    val transcription : StateFlow<Transcription>
        get() = _transcription.asStateFlow()

    private val _summary : MutableStateFlow<Summary> = MutableStateFlow(Summary(UUID.randomUUID(), UUID.randomUUID(), Status.NOT_STARTED, "Loading..."))
    val summary : StateFlow<Summary>
        get() = _summary.asStateFlow()




    private val _text = MutableLiveData<String>().apply {
        value = "This is Folder Fragment"
    }
    val text: LiveData<String> = _text


    init {
        //Prevent app crashing when the db returns a null object when nothing got selected
            viewModelScope.launch {
                gptBrosRepository.listenOnSession(sessionId).collect() {
                    largeLog(TAG, "INCOMING:"+it.toString())
                    largeLog(TAG, "CURRENT:"+_session.value.toString())
                    _session.value = it;
                    largeLog(TAG, "NEW:"+it.toString())
                }
            }
            viewModelScope.launch {
                gptBrosRepository.listenOnRecording(sessionId).collect() {
                    largeLog(TAG, "INCOMING:"+it.toString())
                    largeLog(TAG, "CURRENT:"+_recording.value.toString())
                    _recording.value = it;
                    largeLog(TAG, "NEW:"+it.toString())
                }
            }
            viewModelScope.launch {
                gptBrosRepository.listenOnTranscription(sessionId).collect() {
                    largeLog(TAG, "INCOMING:"+it.toString())
                    largeLog(TAG, "CURRENT:"+_transcription.value.toString())
                    _transcription.value = it;
                    largeLog(TAG, "NEW:"+it.toString())
                }
            }
            viewModelScope.launch {
                gptBrosRepository.listenOnSummary(sessionId).collect() {
                    largeLog(TAG, "INCOMING:"+it.toString())
                    largeLog(TAG, "CURRENT:"+_summary.value.toString())
                    _summary.value = it;
                    largeLog(TAG, "NEW:"+it.toString())
                }
            }
    }
    private fun largeLog(tag: String?, content: String) {
        if (content.length > 4000) {
            Log.d(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            Log.d(tag, content)
        }
    }
}



class AccountViewModelFactory(private val sessionId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(sessionId) as T
    }
}