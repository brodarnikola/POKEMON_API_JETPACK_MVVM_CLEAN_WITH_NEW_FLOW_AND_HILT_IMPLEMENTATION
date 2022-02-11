package com.nikola_brodar.domain.usecase

import com.nikola_brodar.domain.ResultState
import com.nikola_brodar.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonUseCase(private val pokemonRepository: PokemonRepository) {

    fun execute() : Flow<ResultState<*>> = pokemonRepository.getAllPokemonsNewFlow(100, 0)

}

class PokemonMovesUseCase(private val pokemonRepository: PokemonRepository) {
    fun execute() : Flow<ResultState<*>> = pokemonRepository.getAllPokemonMovesFromDB()
}