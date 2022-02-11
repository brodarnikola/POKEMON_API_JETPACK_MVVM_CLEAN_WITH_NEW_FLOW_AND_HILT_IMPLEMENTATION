package com.nikola_brodar.domain.model

import com.google.gson.annotations.SerializedName

data class MainPokemon (


    var name: String = "",

    val sprites: PokemonSprites = PokemonSprites(),

    var stats: List<PokemonStats> = listOf(),

    var moves: List<PokemonMainMoves> = listOf(),

)