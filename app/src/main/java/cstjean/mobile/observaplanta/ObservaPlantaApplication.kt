package cstjean.mobile.observaplanta

import android.app.Application

class ObservaPlantaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        PlanteRepository.initialize(this)
    }
}