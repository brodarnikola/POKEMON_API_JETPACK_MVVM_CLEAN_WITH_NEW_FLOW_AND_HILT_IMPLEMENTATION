package com.nikola_brodar.pokemonapi.genericexample

class ProducerPhone<out T: Phone>(val phone: T)  {

    fun get() : T {
        return phone
    }

}