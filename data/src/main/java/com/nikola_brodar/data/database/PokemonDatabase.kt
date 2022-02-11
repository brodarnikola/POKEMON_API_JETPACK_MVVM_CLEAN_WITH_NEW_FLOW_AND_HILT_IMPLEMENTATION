/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nikola_brodar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikola_brodar.data.database.dao.*
import com.nikola_brodar.data.database.model.*


private const val DB_NAME = "pokemon_database"

/**
 * The Room database for this app
 */
@Database(entities = [DBMainPokemon::class, DBPokemonMoves::class, DBPokemonStats::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDAO(): PokemonDAO

    companion object {

        fun create(context: Context): PokemonDatabase {

            return Room.databaseBuilder(
                context,
                PokemonDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
