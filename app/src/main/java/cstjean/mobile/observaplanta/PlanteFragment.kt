package cstjean.mobile.observaplanta
import androidx.navigation.fragment.navArgs
import cstjean.mobile.observaplanta.databinding.FragmentPlanteBinding
import java.util.*

class PlanteFragment {
    private var _binding: FragmentPlanteBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }
    private val plantesListViewModel: PlantesListViewModel by viewModels()
    private val args: PlanteFragmentArgs by navArgs()
    private val planteViewModel: PlanteViewModel by viewModels {
        PlanteViewModelFactory(args.planteId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}