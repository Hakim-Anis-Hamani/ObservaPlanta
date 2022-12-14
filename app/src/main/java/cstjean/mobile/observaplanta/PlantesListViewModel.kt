package cstjean.mobile.observaplanta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.observaplanta.plante.Plante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlantesListViewModel : ViewModel(){
    private val planteRepository = PlanteRepository.get()
    private val _plantes: MutableStateFlow<List<Plante>> = MutableStateFlow(emptyList())
    val cartes: StateFlow<List<Plante>>
        get() = _plantes.asStateFlow()
    init {
        viewModelScope.launch {
            planteRepository.getPlantes().collect {
                _plantes.value = it
            }
        }
    }
    suspend fun addPlante(plante: Plante) {
        planteRepository.addPlante(plante)
    }

    suspend fun removePlante(plante: Plante) {
        planteRepository.removePlante(plante)
    }
}