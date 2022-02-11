package com.nikola_brodar.domain.model

import com.google.gson.annotations.SerializedName

data class PokemonStats (

    @SerializedName("base_stat")
    val baseStat: Int = 0,

    val stat: PokemonStatsName = PokemonStatsName()
)