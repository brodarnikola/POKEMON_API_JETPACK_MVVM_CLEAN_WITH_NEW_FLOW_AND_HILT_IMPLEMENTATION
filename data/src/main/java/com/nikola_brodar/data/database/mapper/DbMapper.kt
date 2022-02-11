package com.nikola_brodar.data.database.mapper

import com.nikola_brodar.data.database.model.DBMainPokemon
import com.nikola_brodar.data.database.model.DBPokemonMoves
import com.nikola_brodar.data.database.model.DBPokemonStats
import com.nikola_brodar.data.networking.model.ApiAllPokemons
import com.nikola_brodar.data.networking.model.ApiMainPokemon
import com.nikola_brodar.domain.model.AllPokemons
import com.nikola_brodar.domain.model.MainPokemon
import com.nikola_brodar.domain.model.PokemonMainMoves
import com.nikola_brodar.domain.model.PokemonStats


interface DbMapper {


    fun mapAllPokemonToDomainAllPokemon( pokemon: ApiAllPokemons): AllPokemons

    fun mapApiPokemonToDomainPokemon( pokemon: ApiMainPokemon): MainPokemon

    fun mapDomainMainPokemonToDBMainPokemon(pokemon: MainPokemon): DBMainPokemon

    fun mapDomainPokemonStatsToDbPokemonStats( pokemon: List<PokemonStats>): List<DBPokemonStats>

    fun mapDomainPokemonMovesToDbPokemonMoves( pokemon: List<PokemonMainMoves>): List<DBPokemonMoves>



    fun mapDbPokemonStatsToDbPokemonStats( pokemon: List<DBPokemonStats>): List<PokemonStats>

    fun mapDbPokemonMovesToDbPokemonMoves( pokemon: List<DBPokemonMoves>): List<PokemonMainMoves>

}