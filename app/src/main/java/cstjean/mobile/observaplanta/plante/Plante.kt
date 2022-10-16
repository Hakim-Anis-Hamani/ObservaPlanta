package cstjean.mobile.observaplanta.plante
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.*
/**
 * Classe principale d'une plante
 *
 * @author Olivier Bilodeau et Hakim-Anis Hamani
 */
@Entity
data class Plante(@PrimaryKey val id: UUID,
                  val nom: String,
                  val nomLatin: String,
                  val ensoleillement : String,
                  val periodeArrosage: String,
                  val photoFilename: String? = null
)

