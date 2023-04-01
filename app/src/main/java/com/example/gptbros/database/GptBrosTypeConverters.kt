package com.example.gptbros.database

import androidx.room.TypeConverter
import com.example.gptbros.model.Stage
import com.example.gptbros.model.Status
import java.util.*

class GptBrosTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

    @TypeConverter
    fun fromStage(stage: Stage) : Int {
        return stage.ordinal
    }

    @TypeConverter
    fun toStage(value : Int) : Stage {
        return enumValues<Stage>()[value]
    }

    @TypeConverter
    fun fromStatus(status: Status) : Int {
        return status.ordinal
    }

    @TypeConverter
    fun toStatus(value : Int) : Status {
        return enumValues<Status>()[value]
    }
}