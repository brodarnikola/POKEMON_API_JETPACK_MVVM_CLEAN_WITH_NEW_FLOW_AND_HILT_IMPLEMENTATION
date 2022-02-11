package com.nikola_brodar.data.networking.model

data class ApiAllPokemons (

    val count: Int = 0,

    val results: List<ApiAllPokemonsData> = listOf(),
)