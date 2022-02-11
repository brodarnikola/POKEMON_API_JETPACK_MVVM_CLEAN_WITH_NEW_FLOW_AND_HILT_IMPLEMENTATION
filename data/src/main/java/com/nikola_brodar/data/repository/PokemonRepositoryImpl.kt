package com.nikola_brodar.data.repository

import android.content.ContentValues
import android.util.Log
import com.nikola_brodar.data.database.PokemonDatabase
import com.nikola_brodar.data.database.mapper.DbMapper
import com.nikola_brodar.data.database.model.DBMainPokemon
import com.nikola_brodar.data.networking.PokemonRepositoryApi
import com.nikola_brodar.domain.ResultState
import com.nikola_brodar.domain.model.*
import com.nikola_brodar.domain.repository.PokemonRepository
import com.vjezba.data.lego.api.BaseDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

/**
 * RepositoryResponseApi module for handling data operations.
 */

class PokemonRepositoryImpl constructor(
    private val dbPokemon: PokemonDatabase,
    private val service: PokemonRepositoryApi,
    private val dbMapper: DbMapper?
) : PokemonRepository, BaseDataSource() {

    override fun getAllPokemonMovesFromDB(): Flow<ResultState<*>> = flow {

        val pokemonsMovesList = dbPokemon.pokemonDAO().getSelectedMovesPokemonData()
        if (pokemonsMovesList.isNotEmpty())
            emit(ResultState.Success(pokemonsMovesList))
        else
            emit(ResultState.Error("Something went wrong when reading data from database", null))
    }

    override fun getAllPokemonsFlow(limit: Int, offset: Int): Flow<ResultState<*>> =
        flow {
            val result = getResult { service.getAllPokemons(limit, offset) }
            when (result) {
                is ResultState.Success -> {
                    val correctResult =
                        dbMapper?.mapAllPokemonToDomainAllPokemon(result.data) ?: AllPokemons()

                    val pokemonID = getRandomSelectedPokemonId(correctResult)
                    val randomPokemonResult =
                        getResult { service.getRandomSelectedPokemon(pokemonID) }

                    when (randomPokemonResult) {
                        is ResultState.Success -> {

                            val correctResult =
                                dbMapper?.mapApiPokemonToDomainPokemon(randomPokemonResult.data)
                            System.out.println("Will it enter here${correctResult}")
                            deleteAllPokemonData()
                            insertPokemonIntoDatabase(correctResult as MainPokemon)
                            emit((ResultState.Success(correctResult)))
                        }
                        is ResultState.Error -> {
                            emit(
                                ResultState.Error(
                                    randomPokemonResult.message,
                                    randomPokemonResult.exception
                                )
                            )
                        }
                    }

                    // .collect()

                    // .collect()


                    // .launchIn(viewModelScope)

                    // emit((ResultState.Success(correctResult)))

                    // return ResultState.Success(correctResult)
                }
                is ResultState.Error -> {
                    emit(ResultState.Error(result.message, result.exception))
                    // return ResultState.Error(result.message, result.exception)
                }
                else -> {
                    emit(ResultState.Error("", null))
                }
            }
        }

    private suspend fun deleteAllPokemonData() {
        coroutineScope {
            val deferreds = listOf(
                async { dbPokemon.pokemonDAO().clearMainPokemonData() },
                async { dbPokemon.pokemonDAO().clearPokemonStatsData() },
                async { dbPokemon.pokemonDAO().clearMPokemonMovesData() }
            )
            deferreds.awaitAll()
        }
    }


    private suspend fun insertPokemonIntoDatabase(pokemonData: MainPokemon) {

        val pokemonMain =
            dbMapper?.mapDomainMainPokemonToDBMainPokemon(pokemonData) ?: DBMainPokemon()
        dbPokemon.pokemonDAO().insertMainPokemonData(pokemonMain)

        val pokemonStats =
            dbMapper?.mapDomainPokemonStatsToDbPokemonStats(pokemonData.stats) ?: listOf()
        dbPokemon.pokemonDAO().insertStatsPokemonData(pokemonStats)

        val pokemonMoves =
            dbMapper?.mapDomainPokemonMovesToDbPokemonMoves(pokemonData.moves) ?: listOf()
        dbPokemon.pokemonDAO().insertMovesPokemonData(pokemonMoves)
    }


    private fun getRandomSelectedPokemonId(allPokemons: AllPokemons): Int {
        val separateString = allPokemons.results.random().url.split("/")
        val pokemonId = separateString.get(separateString.size - 2)
        Log.d(
            ContentValues.TAG,
            "Id is: ${pokemonId.toInt()}"
        )
        return pokemonId.toInt()
    }


    override suspend fun getAllPokemons(limit: Int, offset: Int): ResultState<*> {
        val result = getResult { service.getAllPokemons(limit, offset) }
        when (result) {
            is ResultState.Success -> {
                val correctResult = dbMapper?.mapAllPokemonToDomainAllPokemon(result.data)
                return ResultState.Success(correctResult)
            }
            is ResultState.Error -> {
                return ResultState.Error(result.message, result.exception)
            }
            else -> {
                return ResultState.Error("", null)
            }
        }
    }

    override suspend fun getRandomSelectedPokemon(id: Int): ResultState<*> { // MainPokemon {
        val result = getResult { service.getRandomSelectedPokemon(id) }
        when (result) {
            is ResultState.Success -> {
                val correctResult = dbMapper?.mapApiPokemonToDomainPokemon(result.data)
                return ResultState.Success(correctResult)
            }
            is ResultState.Error -> {
                return ResultState.Error(result.message, result.exception)
            }
            else -> {
                return ResultState.Error("", null)
            }
        }
    }

}
