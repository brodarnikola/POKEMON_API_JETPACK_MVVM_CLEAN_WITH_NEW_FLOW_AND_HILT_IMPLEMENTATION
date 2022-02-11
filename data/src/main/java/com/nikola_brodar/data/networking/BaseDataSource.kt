package com.vjezba.data.lego.api

import com.nikola_brodar.domain.ResultState
import retrofit2.Response

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResultState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultState.Success(
                    body
                )
            }
            val errorMessage = "${response.code()} ${response.message()}"
            return error( errorMessage, null )
        } catch (e: Exception) {
            return error( "Error is: ", e )
        }
    }

    private fun <T> error( errorMessage: String, message: Exception?): ResultState<T> {
        //Timber.e(message)
        return ResultState.Error( errorMessage, message)
    }

}

