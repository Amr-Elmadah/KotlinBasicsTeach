package com.amr.kotlinteach.network.interfaces

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

interface CatFactService {

    @GET("facts")
    fun getFacts(@Query("max_length") length: Int, @Query("limit") limit: Int): Call<JsonElement>
}
