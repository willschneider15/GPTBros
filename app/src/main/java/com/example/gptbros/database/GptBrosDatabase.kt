package com.example.gptbros.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gptbros.model.*

@Database(entities = [Session::class, Recording::class, Transcription::class, Summary::class, wipeClass::class], version = 5)
@TypeConverters(GptBrosTypeConverters::class)
abstract class GptBrosDatabase : RoomDatabase(){

    abstract fun gptBrosDao(): GptBrosDao
}