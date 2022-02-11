package com.nikola_brodar.domain.model

import com.google.gson.annotations.SerializedName


data class PokemonSprites (
    @SerializedName("back_default")
    var backDefault: String = "",
    @SerializedName("front_default")
    var frontDefault: String = ""
)