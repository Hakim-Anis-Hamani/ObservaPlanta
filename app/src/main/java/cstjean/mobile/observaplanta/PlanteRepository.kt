package cstjean.mobile.observaplanta

import android.content.Context
import androidx.room.Room
import cstjean.mobile.observaplanta.database.MIGRATION_1_2
import cstjean.mobile.observaplanta.database.PlanteDatabase
import cstjean.mobile.observaplanta.plante.Plante
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "plante-database"

class PlanteRepository private constructor(context: Context, private val coroutineScope: CoroutineScope = GlobalScope) {
    private val database: PlanteDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            PlanteDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(MIGRATION_1_2)
        .build()

    fun getPlantes(): Flow<List<Plante>> = database.planteDao().getPlantes()
    suspend fun getPlante(id: UUID): Plante = database.planteDao().getPlante(id)

    suspend fun addPlante(plante: Plante) {
        database.planteDao().addPlante(plante)
    }

    suspend fun removePlante(plante: Plante) {
        database.planteDao().removePlante(plante)
    }

    fun updatePlante(plante: Plante) {
        coroutineScope.launch {
            database.planteDao().updatePlante(plante)
        }
    }
    companion object {
        private var INSTANCE: PlanteRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PlanteRepository(context)
            }
        }

        fun get(): PlanteRepository {
            return INSTANCE
                ?: throw IllegalStateException("PlanteRepository doit être initialisé...")
        }
    }
}