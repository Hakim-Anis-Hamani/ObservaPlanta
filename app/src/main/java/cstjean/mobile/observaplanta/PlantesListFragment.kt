package cstjean.mobile.observaplanta

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cstjean.mobile.observaplanta.databinding.FragmentPlantesListBinding
import cstjean.mobile.observaplanta.plante.Plante
import kotlinx.coroutines.launch
import java.util.*

/**
 * Fragment pour la liste des plantes.
 *
 * @author Hakim-Anis Hamani
 */
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
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.plantesRecyclerView.layoutManager = GridLayoutManager(context,3)
        }
        else{
            binding.plantesRecyclerView.layoutManager = GridLayoutManager(context,2)
        }

        return binding.root
    }

    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */
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

        /**Variable text watcher permettant de capter les modification dans la barre de recherche à tout moment*/
        val textwatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //...
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //...
            }

            override fun afterTextChanged(s: Editable?) {
                val nomPlante = s.toString()
                if (nomPlante != "" ){
                    plantesListViewModel.getPlantesParNom("%$nomPlante%")
                }
                else{
                    plantesListViewModel.getPlantes()
                }
            }

        }
        binding.plantesSearchBar.addTextChangedListener(textwatcher)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            /**
             * Instanciation du menu
             *
             * @param menu menu de l'interface.
             * @param menuInflater classe de l'initialisation du xml afin d'instancer l'interface
             */
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_plantes_list, menu)
            }

            /**
             * Lorsque un item du menu est utiliser
             *
             * @param menu menu de l'interface.
             * @param menuInflater classe de l'initialisation du xml afin d'instancer l'interface.
             */
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.nouvelle_carte -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val nouvellePlante =
                                Plante(
                                    UUID.randomUUID(),
                                "",
                                "",
                                "",
                                ""
                                )

                            plantesListViewModel.addPlante(nouvellePlante)
                            findNavController().navigate(
                                PlantesListFragmentDirections.showPlanteDetail(nouvellePlante.id)
                            )
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