package com.nikola_brodar.pokemonapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikola_brodar.data.database.model.DBPokemonMoves
import com.nikola_brodar.pokemonapi.databinding.PokemonMovesListBinding

class PokemonMovesAdapter(
    var pokemonStatsList: MutableList<DBPokemonMoves>
) : RecyclerView.Adapter<PokemonMovesAdapter.PokemonViewHolder>() {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindItem( pokemonStatsList[position] )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonMovesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class PokemonViewHolder(private val binding: PokemonMovesListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: DBPokemonMoves) {

            binding.apply {
                tvTemp.text = "Move name: " + item.name
                tvMax.text = "Move url: " + item.url
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonStatsList.size
    }

    fun updateDevices(updatedDevices: MutableList<DBPokemonMoves>) {
        pokemonStatsList.addAll(updatedDevices)
        notifyItemRangeInserted(pokemonStatsList.size, updatedDevices.size)
    }

}