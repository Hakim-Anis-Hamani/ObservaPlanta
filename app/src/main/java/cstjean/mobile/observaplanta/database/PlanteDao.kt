package cstjean.mobile.observaplanta.database

import kotlinx.coroutines.flow.Flow
import java.util.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cstjean.mobile.observaplanta.plante.Plante

@Dao
interface PlanteDao {
    @Query("SELECT * FROM plante")
    fun getPlantes(): Flow<List<Plante>>

    @Query("SELECT * FROM plante WHERE id=(:id)")
    suspend fun getPlante(id: UUID): Plante

    @Insert
    suspend fun addPlante(plante: Plante)

    @Delete
    suspend fun removePlante(plante: Plante)

    @Update
    suspend fun updatePlante(plante: Plante)
}