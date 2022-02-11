/*
 * Copyright 2020 Google LLC
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

package com.nikola_brodar.data.networking


import com.nikola_brodar.data.networking.model.ApiAllPokemons
import com.nikola_brodar.data.networking.model.ApiMainPokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonRepositoryApi {

    @GET("pokemon")
    @Headers("Content-Type: application/json")
    suspend fun getAllPokemons(  @Query("limit") limit: Int, @Query("offset") offset: Int  ): Response<ApiAllPokemons>



    @GET("pokemon/{id}")
    @Headers("Content-Type: application/json")
    suspend fun getRandomSelectedPokemon( @Path("id") id: Int ):  Response<ApiMainPokemon>


}
