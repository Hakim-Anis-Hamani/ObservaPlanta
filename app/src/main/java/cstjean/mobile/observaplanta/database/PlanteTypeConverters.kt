package cstjean.mobile.observaplanta.database

import androidx.room.TypeConverter
import java.util.*
/**
 * Type converters de la base de données.
 *
 * @author Hakim-Anis Hamani
 */
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