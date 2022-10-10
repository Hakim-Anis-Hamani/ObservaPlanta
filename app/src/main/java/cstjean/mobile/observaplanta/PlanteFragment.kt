package cstjean.mobile.observaplanta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cstjean.mobile.observaplanta.databinding.FragmentPlanteBinding
import cstjean.mobile.observaplanta.plante.Plante
import kotlinx.coroutines.launch

class PlanteFragment : Fragment() {
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

    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    /**
     * Lorsque la vue est créée.
     *
     * @param view La vue créée.
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            planteNom.doOnTextChanged { text, _, _, _ ->
                planteViewModel.updateCarte { oldPlante ->
                    oldPlante.copy(nom = text.toString())
                }
            }

            planteNomLatin.doOnTextChanged { text, _, _, _ ->
                planteViewModel.updateCarte { oldPlante ->
                    oldPlante.copy(nomLatin = text.toString())
                }
            }

            ArrayAdapter.createFromResource(
                spinnerEnsoleillement.context,
                R.array.type_ensoleillement,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEnsoleillement.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                spinnerArrosage.context,
                R.array.periode_arrosage,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerArrosage.adapter = adapter
            }

            spinnerEnsoleillement.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedValue = parent?.getItemAtPosition(position)
                        
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    planteViewModel.plante.collect { plante ->
                        plante?.let { updateUi(it) }
                    }
                }
            }

        }
    }

    private suspend fun updateUi(plante: Plante) {
        binding.apply {

            if (planteNom.text.toString() != plante.nom) {
                planteNom.setText(plante.nom)
            }

            if (planteNomLatin.text.toString() != plante.nomLatin) {
                planteNomLatin.setText(plante.nomLatin)
            }

            carteSupprimer.setOnClickListener {
                findNavController().navigate(
                    PlanteFragmentDirections.supprimerPlante()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    plantesListViewModel.removePlante(plante)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}