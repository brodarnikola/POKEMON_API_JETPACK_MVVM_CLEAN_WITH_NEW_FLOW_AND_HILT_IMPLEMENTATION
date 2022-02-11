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

package com.nikola_brodar.data.di

import com.nikola_brodar.data.database.PokemonDatabase
import com.nikola_brodar.data.database.mapper.DbMapper
import com.nikola_brodar.data.networking.PokemonRepositoryApi
import com.nikola_brodar.data.repository.PokemonRepositoryImpl
import com.nikola_brodar.domain.repository.PokemonRepository
import com.nikola_brodar.domain.usecase.PokemonMovesUseCase
import com.nikola_brodar.domain.usecase.PokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetAllPokemons(pokemonRepository: PokemonRepository) = PokemonUseCase(pokemonRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAllPokemonMovesFromDB(pokemonRepository: PokemonRepository) = PokemonMovesUseCase(pokemonRepository)

}
