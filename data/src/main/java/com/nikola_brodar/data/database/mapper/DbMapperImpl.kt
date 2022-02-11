package com.nikola_brodar.data.database.mapper

import com.nikola_brodar.data.database.model.DBMainPokemon
import com.nikola_brodar.data.database.model.DBPokemonMoves
import com.nikola_brodar.data.database.model.DBPokemonStats
import com.nikola_brodar.data.networking.model.ApiAllPokemons
import com.nikola_brodar.data.networking.model.ApiMainPokemon
import com.nikola_brodar.domain.model.*

class DbMapperImpl : DbMapper {

    override fun mapDomainPokemonStatsToDbPokemonStats(pokemon: List<PokemonStats>): List<DBPokemonStats> {
        return pokemon.map {
            DBPokemonStats(
                it.baseStat,
                it.stat.name
            )
        }
    }

    override fun mapDomainPokemonMovesToDbPokemonMoves(pokemon: List<PokemonMainMoves>): List<DBPokemonMoves> {
        return pokemon.map {
            DBPokemonMoves(
                it.move.name,
                it.move.url
            )
        }
    }


    override fun mapDbPokemonStatsToDbPokemonStats(pokemon: List<DBPokemonStats>): List<PokemonStats> {
        return pokemon.map {
            PokemonStats(
                it.baseStat,
                PokemonStatsName(
                    it.name
                )
            )
        }
    }

    override fun mapDbPokemonMovesToDbPokemonMoves(pokemon: List<DBPokemonMoves>): List<PokemonMainMoves> {
        return pokemon.map {
            PokemonMainMoves(
                PokemonMove(
                    it.name,
                    it.url
                )
            )
        }
    }

    override fun mapAllPokemonToDomainAllPokemon(pokemon: ApiAllPokemons): AllPokemons {
        return with(pokemon) {
            AllPokemons(
                count,
                results.map {
                    AllPokemonsData(
                        it.name,
                        it.url
                    )
                }
            )
        }
    }

    override fun mapApiPokemonToDomainPokemon(pokemon: ApiMainPokemon): MainPokemon {
        return with(pokemon) {
            MainPokemon(
                name,
                PokemonSprites(
                    sprites.backDefault,
                    sprites.frontDefault
                ),
                stats.map {
                    with(it) {
                        PokemonStats(
                            baseStat,
                            stat = PokemonStatsName(
                                stat.name
                            )
                        )
                    }
                },
                moves.map {
                    PokemonMainMoves(
                        PokemonMove(
                            it.move.name,
                            it.move.url
                        )
                    )
                }
            )
        }
    }

    override fun mapDomainMainPokemonToDBMainPokemon(pokemon: MainPokemon): DBMainPokemon {
        return with(pokemon) {
            DBMainPokemon(
                name,
                backDefault = sprites.backDefault,
                frontDefault = sprites.frontDefault
            )
        }
    }




}
