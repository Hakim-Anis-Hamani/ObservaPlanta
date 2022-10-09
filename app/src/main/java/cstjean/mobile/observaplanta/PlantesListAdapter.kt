package cstjean.mobile.observaplanta

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.observaplanta.databinding.ListItemPlanteBinding
import cstjean.mobile.observaplanta.plante.Plante
import java.util.*

/**
 * ViewHolder pour Le RecyclerView des Cartes.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Olga Cazacioc et Hakim-Anis Hamani
 */
class PlanteHolder(private val binding: ListItemPlanteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe une carte à ce ViewHolder.
     *
     * @param carte La carte associé.
     */
    fun bind(plante: Plante, onPlanteClicked: (planteId: UUID) -> Unit) {
        binding.planteNom.text = plante.nom
        binding.planteNomLatin.text = plante.nom
        binding.root.setOnClickListener {
            onPlanteClicked(plante.id)
        }
    }

}

/**
 * Adapter pour notre RecyclerView de Cartes.
 *
 * @property cartes Liste des cartes à afficher.
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