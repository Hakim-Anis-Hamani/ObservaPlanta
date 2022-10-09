package cstjean.mobile.observaplanta

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.javafaker.CreditCardType
import com.github.javafaker.Faker
import cstjean.mobile.observaplanta.databinding.FragmentPlantesListBinding
import cstjean.mobile.observaplanta.plante.PeriodeArossage
import cstjean.mobile.observaplanta.plante.Plante
import cstjean.mobile.observaplanta.plante.TypeEnsoleillement
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "PlantesListFragment"

class PlantesListFragment : Fragment(){
    private var _binding: FragmentPlantesListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val plantesListViewModel: PlantesListViewModel by viewModels()

    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlantesListBinding.inflate(layoutInflater, container, false)
        binding.plantesRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                plantesListViewModel.cartes.collect { plantes ->
                    binding.plantesRecyclerView.adapter = PlantesListAdapter(plantes){ planteId ->
                        findNavController().navigate(
                            PlantesListFragmentDirections.showPlanteDetail(planteId)
                        )
                    }
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_plantes_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nouvelle_carte -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val nouvellePlante =
                                Plante(
                                    UUID.randomUUID(),
                                "",
                                "",
                                TypeEnsoleillement.PLEIN_SOLEIL,
                                PeriodeArossage.HEBDOMADAIRE
                                )

                            plantesListViewModel.addPlante(nouvellePlante)
                        }
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }




    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}