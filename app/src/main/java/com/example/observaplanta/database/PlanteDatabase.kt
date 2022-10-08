package com.example.observaplanta.database

import androidx.room.*
import com.example.observaplanta.plante.Plante

@Database(entities = [ Plante::class ], version=1, exportSchema = false)
@TypeConverters(PlanteTypeConverters::class)
abstract class PlanteDatabase : RoomDatabase(){
    abstract fun planteDao(): PlanteDao
}
