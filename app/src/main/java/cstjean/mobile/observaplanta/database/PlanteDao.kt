package cstjean.mobile.observaplanta.database

import kotlinx.coroutines.flow.Flow
import java.util.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cstjean.mobile.observaplanta.plante.Plante
/**
 * Interface Dao pour la gestion des commandes de la base de données.
 *
 * @author Hakim-Anis Hamani
 */
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

    @Query("SELECT * FROM plante WHERE nom LIKE :nomPlante")
    fun getPlantesParNom(nomPlante:String) : Flow<List<Plante>>
}