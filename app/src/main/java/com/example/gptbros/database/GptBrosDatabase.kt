package com.example.gptbros.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gptbros.model.AudioSession

@Database(entities = [AudioSession::class], version = 1)
@TypeConverters(GptBrosTypeConverters::class)
abstract class GptBrosDatabase : RoomDatabase(){
    abstract fun gptBrosDao(): GptBrosDao
}