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
data class Plante(@PrimaryKey
                  /**
                   * Id de la plante en BD.
                   */
                  val id: UUID,
                  /**
                   * Nom de la plante.
                   */
                  val nom: String,
                  /**
                   * Nom latin de la plante.
                   */
                  val nomLatin: String,
                  /**
                   * Période d'ensoleillement de la plante.
                   */
                  val ensoleillement : String,
                  /**
                   * Période d'arrosage de la plante.
                   */
                  val periodeArrosage: String,
                  /**
                   * Nom du fichier photo de la plante.
                   */
                  val photoFilename: String? = null
)

