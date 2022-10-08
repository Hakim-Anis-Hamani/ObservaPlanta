package com.example.observaplanta

import android.app.Application
import com.example.observaplanta.plante.Plante

class ObservaPlantaApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        PlanteRepository.initialize(this)
    }
}