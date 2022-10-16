package cstjean.mobile.observaplanta

import android.app.Application

/**
 * Application principale pour l'initialisation du fragment.
 *
 * @author Hakim-Anis Hamani
 */
class ObservaPlantaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        PlanteRepository.initialize(this)
    }
}