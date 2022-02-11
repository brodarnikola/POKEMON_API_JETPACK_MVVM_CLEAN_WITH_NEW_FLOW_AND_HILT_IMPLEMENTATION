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

package com.nikola_brodar.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nikola_brodar.data.BuildConfig
import com.nikola_brodar.data.networking.PokemonRepositoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



private const val RETROFIT_BASE_URL = "https://pokeapi.co/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModuleHilt {


    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(  interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory( gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit( converterFactory: GsonConverterFactory, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(RETROFIT_BASE_URL)
            .addConverterFactory(converterFactory)
    }

    @Singleton
    @Provides
    fun provideService(  retrofit: Retrofit.Builder): PokemonRepositoryApi {
        return retrofit
            .build()
            .create(PokemonRepositoryApi::class.java)
    }

}
