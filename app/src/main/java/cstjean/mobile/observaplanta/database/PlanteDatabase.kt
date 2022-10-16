package cstjean.mobile.observaplanta.database

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cstjean.mobile.observaplanta.plante.Plante

@Database(entities = [ Plante::class ], version=2, exportSchema = false)
@TypeConverters(PlanteTypeConverters::class)
abstract class PlanteDatabase : RoomDatabase(){
    abstract fun planteDao(): PlanteDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE plante ADD COLUMN photoFilename TEXT"
        )
    }
}
