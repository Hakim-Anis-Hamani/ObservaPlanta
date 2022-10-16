package cstjean.mobile.observaplanta

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.observaplanta.databinding.ListItemPlanteBinding
import cstjean.mobile.observaplanta.plante.Plante
import java.util.*

/**
 * ViewHolder pour Le RecyclerView des Plantes.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Hakim-Anis Hamani
 */
class PlanteHolder(private val binding: ListItemPlanteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe une carte à ce ViewHolder.
     *
     * @param plante La plante associé.
     */
    fun bind(plante: Plante, onPlanteClicked: (planteId: UUID) -> Unit) {
        binding.planteNom.text = plante.nom
        binding.planteNomLatin.text = plante.nomLatin
        if (binding.imagePlante.drawable == null) {
            binding.imagePlante.setImageResource(R.drawable.default_plant)
        }
        binding.root.setOnClickListener {
            onPlanteClicked(plante.id)
        }
    }

}

/**
 * Adapter pour notre RecyclerView de Plantes.
 *
 * @property plantes Liste des plantes à afficher.
 *
 * @author Gabriel T. St-Hilaire
 */
class PlantesListAdapter(private val plantes: List<Plante>, private val onPlanteClicked: (carteId: UUID) -> Unit) :
    RecyclerView.Adapter<PlanteHolder>() {

    /**
     * Lors de la création des ViewHolder.
     *
     * @param parent Layout dans lequel la nouvelle vue
     *                 sera ajoutée quand elle sera liée à une position.
     * @param viewType Le type de vue de la nouvelle vue.
     *
     * @return Un ViewHolder pour notre cellule.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlanteBinding.inflate(inflater, parent, false)
        return PlanteHolder(binding)
    }

    /**
     * Associe un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à utiliser.
     * @param position La position dans la liste qu'on souhaite utiliser.
     */
    override fun onBindViewHolder(holder: PlanteHolder, position: Int) {
        val plante = plantes[position]
        holder.bind(plante, onPlanteClicked)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = plantes.size
}