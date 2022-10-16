package cstjean.mobile.observaplanta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cstjean.mobile.observaplanta.plante.Plante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
/**
 * ViewModel pour une plante.
 *
 * @author Hakim-Anis Hamani
 */
class PlanteViewModel(planteId: UUID): ViewModel() {
    private val planteRepository = PlanteRepository.get()
    private val _plante: MutableStateFlow<Plante?> = MutableStateFlow(null)
    val plante: StateFlow<Plante?>
        get() = _plante.asStateFlow()
    init {
        viewModelScope.launch {
            _plante.value = planteRepository.getPlante(planteId)
        }
    }

    fun updateCarte(onUpdate: (Plante) -> Plante) {
        _plante.update { oldPlante ->
            oldPlante?.let { onUpdate(it) }
        }
    }
    override fun onCleared() {
        super.onCleared()
        plante.value?.let { planteRepository.updatePlante(it) }
    }
}
class PlanteViewModelFactory(private val planteId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlanteViewModel(planteId) as T
    }
}