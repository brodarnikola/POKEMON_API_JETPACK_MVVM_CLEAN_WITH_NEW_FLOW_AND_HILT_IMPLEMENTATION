package com.nikola_brodar.pokemonapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikola_brodar.domain.model.PokemonStats
import com.nikola_brodar.pokemonapi.databinding.PokemonStatsListBinding

class PokemonAdapter(
    var pokemonStatsList: MutableList<PokemonStats>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindItem( pokemonStatsList[position] )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonStatsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class PokemonViewHolder(private val binding: PokemonStatsListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: PokemonStats) {

            binding.apply {
                tvTemp.text = item.stat.name
                tvMax.text = " " + item.baseStat
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonStatsList.size
    }

    fun updateDevices(updatedDevices: MutableList<PokemonStats>) {
        pokemonStatsList.addAll(updatedDevices)
        notifyItemRangeInserted(pokemonStatsList.size, updatedDevices.size)
    }

}