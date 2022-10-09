package cstjean.mobile.observaplanta.plante
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.*

@Entity
data class Plante(@PrimaryKey val id: UUID,
                  val nom: String,
                  val nomLatin: String,
                  val ensoleillement : TypeEnsoleillement,
                  val periodeArrosage: PeriodeArossage
){
    //TODO placer image de la plante avec une image de base
}

