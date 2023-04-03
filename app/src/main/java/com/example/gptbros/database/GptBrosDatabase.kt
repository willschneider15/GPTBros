package com.example.gptbros.database

import androidx.room.Database
import androidx.room.Insert
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gptbros.model.Recording
import com.example.gptbros.model.Session
import com.example.gptbros.model.Summary
import com.example.gptbros.model.Transcription

@Database(entities = [Session::class, Recording::class, Transcription::class, Summary::class], version = 4)
@TypeConverters(GptBrosTypeConverters::class)
abstract class GptBrosDatabase : RoomDatabase(){

    abstract fun gptBrosDao(): GptBrosDao
}