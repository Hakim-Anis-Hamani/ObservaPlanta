package com.example.observaplanta

import android.content.Context
import androidx.room.Room
import com.example.observaplanta.database.PlanteDatabase
import com.example.observaplanta.plante.Plante
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
        .build()

    fun getCartes(): Flow<List<Plante>> = database.planteDao().getPlantes()
    suspend fun getCarte(id: UUID): Plante = database.planteDao().getPlante(id)

    suspend fun addCarte(plante: Plante) {
        database.planteDao().addPlante(plante)
    }

    suspend fun removeCarte(plante: Plante) {
        database.planteDao().removePlante(plante)
    }

    fun updateCarte(plante: Plante) {
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
                ?: throw IllegalStateException("CarteRepository doit être initialisé...")
        }
    }
}