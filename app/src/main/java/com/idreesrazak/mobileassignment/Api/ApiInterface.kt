package com.idreesrazak.mobileassignment.Api

import retrofit2.http.GET
import retrofit2.Call
import com.idreesrazak.mobileassignment.model.ExchangeRates
import retrofit2.Response
import retrofit2.http.Query

interface ApiInterface {

    @GET("/latest")
    fun getRates(@Query("from") base: String): Call<ExchangeRates>

}