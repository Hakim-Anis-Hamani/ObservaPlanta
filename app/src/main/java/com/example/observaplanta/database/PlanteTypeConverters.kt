package com.example.observaplanta.database

import androidx.room.TypeConverter
import com.example.observaplanta.plante.TypeEnsoleillement
import java.util.*

class PlanteTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
}