package com.example.gptbros.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gptbros.adapters.DatabaseTupleAdapter
import com.example.gptbros.model.FolderListItem
import com.example.gptbros.model.GptBrosRepository
import com.example.gptbros.model.Status
import com.example.gptbros.model.Summary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SummaryDetailViewModel(sessionUUID : UUID ) : ViewModel(){
    //val sessionUUID : UUID = UUID.fromString("9B3FA018-D5A2-4ABC-BB8A-41BC5D0D33FB")
    private val gptBrosRepository = GptBrosRepository.get()
    //Find a better way to init an empty summary
    private val _summaryItem : MutableStateFlow<Summary> = MutableStateFlow(Summary(UUID.randomUUID(), UUID.randomUUID(),Status.ERROR, ""))
    val summaryItem : StateFlow<Summary>
        get() = _summaryItem.asStateFlow()

    init {
        viewModelScope.launch {
            gptBrosRepository.getSummaryFlow(sessionUUID).collect() {
                _summaryItem.value = it
            }
        }
    }
}

class SummaryDetailViewModelFactory(private val sessionUUID: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        return SummaryDetailViewModel(sessionUUID) as T
    }
}