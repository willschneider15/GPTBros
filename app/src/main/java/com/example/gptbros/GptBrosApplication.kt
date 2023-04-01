package com.example.gptbros

import android.app.Application
import com.example.gptbros.model.GptBrosRepository

class GptBrosApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        GptBrosRepository.initialize(this)
    }
}