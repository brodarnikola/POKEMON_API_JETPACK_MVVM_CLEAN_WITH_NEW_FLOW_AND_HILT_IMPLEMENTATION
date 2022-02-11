package com.nikola_brodar.data.networking.model

data class ApiMainPokemon (

    val name: String = "",

    val sprites: ApiPokemonSprites = ApiPokemonSprites(),

    val stats: List<ApiPokemonStats> = listOf(),

    val moves: List<ApiPokemonMainMoves> = listOf(),
)