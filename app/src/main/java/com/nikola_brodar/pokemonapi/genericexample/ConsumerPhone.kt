package com.nikola_brodar.pokemonapi.genericexample

class ConsumerPhone<in T: Phone> {

    fun printPhone(phone: T) {
        println("Geeks -> Phone is: ".plus(phone))
    }

}