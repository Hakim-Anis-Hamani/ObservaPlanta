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
    /**
     * Fonction qui get la liste des plantes depuis la BD.
     */
    @Query("SELECT * FROM plante")
    fun getPlantes(): Flow<List<Plante>>

    /**
     * Fonction qui get une plante sélectionné depuis la BD.
     */
    @Query("SELECT * FROM plante WHERE id=(:id)")
    suspend fun getPlante(id: UUID): Plante

    /**
     * Fonction qui ajoute une plante dans la BD.
     */
    @Insert
    suspend fun addPlante(plante: Plante)

    /**
     * Fonction qui delete une plante sélectionné dans la BD.
     */
    @Delete
    suspend fun removePlante(plante: Plante)

    /**
     * Fonction qui met à jour une plante dans la BD.
     */
    @Update
    suspend fun updatePlante(plante: Plante)

    /**
     * Fonction qui recherche des plante selon leur nom dans la BD.
     */
    @Query("SELECT * FROM plante WHERE nom LIKE :nomPlante")
    fun getPlantesParNom(nomPlante:String) : Flow<List<Plante>>
}