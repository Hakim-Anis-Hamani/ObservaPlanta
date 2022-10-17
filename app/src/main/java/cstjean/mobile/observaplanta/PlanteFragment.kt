package cstjean.mobile.observaplanta
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.doOnLayout
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
import java.io.File
import java.util.*

/**
 * Fragment pour la gestion d'une plante.
 *
 * @author  Olivier Bilodeau et Hakim-Anis Hamani
 */
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

    private val prendrePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { photoPrise: Boolean ->
            if (photoPrise && photoFilename != null) {
                planteViewModel.updateCarte { oldPlante ->
                    oldPlante.copy(photoFilename = photoFilename)
                }
            }
        }
    private var photoFilename: String? = null

    private var selection: String = ""
    private var selPosition = 0

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
                        selection = parent?.getItemAtPosition(position).toString()

                        selPosition = position

                        planteViewModel.updateCarte { oldPlante ->
                            oldPlante.copy(
                                ensoleillement = selection
                            )
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            spinnerArrosage.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selection = parent?.getItemAtPosition(position).toString()

                        planteViewModel.updateCarte { oldPlante ->
                            oldPlante.copy(
                                periodeArrosage = selection
                            )
                        }
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

            planteCamera.setOnClickListener {
                photoFilename = "IMG_${Date()}.JPG"
                val photoFichier = File(requireContext().applicationContext.filesDir, photoFilename)
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "cstjean.mobile.fileprovider",
                    photoFichier
                )
                prendrePhoto.launch(photoUri)
            }

            val cameraIntent = prendrePhoto.contract.createIntent(
                requireContext(),
                Uri.parse("")
            )

            planteCamera.isEnabled = canResolveIntent(cameraIntent)

        }
    }

    /**
     * Fonction permettant l'update des champs de la plante.
     *
     * @param plante La plante modifié.
     */
    private suspend fun updateUi(plante: Plante) {

        binding.apply {

            if (planteNom.text.toString() != plante.nom) {
                planteNom.setText(plante.nom)
            }

            if (planteNomLatin.text.toString() != plante.nomLatin) {
                planteNomLatin.setText(plante.nomLatin)
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            builder.setTitle(getString(R.string.confirmation))
            builder.setMessage(getString(R.string.confirm_question))

            builder.setPositiveButton(
                "YES"
            ) { dialog, _ ->
                findNavController().navigate(
                    PlanteFragmentDirections.supprimerPlante()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    plantesListViewModel.removePlante(plante)
                }
                dialog.dismiss()
            }

            builder.setNegativeButton(
                "NO"
            ) { dialog, _ ->
                dialog.dismiss()
            }

            planteSupprimer.setOnClickListener {
                val alert = builder.create()
                alert.show()
            }


            btnPartager.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_TEXT, plante.nom)
                }

                val chooserIntent = Intent.createChooser(
                    intent,
                    getString(R.string.partager)
                )

                startActivity(chooserIntent)
            }
            imagePlante.setImageResource(R.drawable.default_plant)
            updatePhoto(plante.photoFilename)
        }
    }

    /**
     * Fonction permettant l'update du preview de la photo.
     */
    private fun updatePhoto(photoFilename: String?) {
        if (binding.imagePlante.tag != photoFilename) {
            val photoFichier = photoFilename?.let {
                File(requireContext().applicationContext.filesDir, it)
            }
            if (photoFichier?.exists() == true) {
                binding.imagePlante.doOnLayout { view ->
                    val scaledBitmap = getScaledBitmap(
                        photoFichier.path,
                        view.width,
                        view.height
                    )
                    binding.imagePlante.setImageBitmap(scaledBitmap)
                    binding.imagePlante.tag = photoFilename
                }
            } else {
                binding.imagePlante.setImageBitmap(null)
                binding.imagePlante.tag = null
            }
        }
    }

    /**
     * Fonction pour valider l'utilisation et la réalisation de l'intent.
     */
    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        return intent.resolveActivity(packageManager) != null
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}