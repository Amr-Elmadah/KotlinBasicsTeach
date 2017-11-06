package com.amr.kotlinteach.data

import com.amr.kotlinteach.data.callbacks.LoadCatFactsCallback
import com.amr.kotlinteach.data.models.Fact
import com.amr.kotlinteach.network.ServiceGenerator
import com.amr.kotlinteach.network.interfaces.CatFactService
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

class CatFactRepository : CatFactDataSource {

    override fun loadCatFacts(length: Int, loadCatFactsCallback: LoadCatFactsCallback) {
        val catFactService = ServiceGenerator.createService(CatFactService::class.java)
        val call = catFactService.getFacts(length, 1000)
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                var facts: List<Fact>
                if (response!!.isSuccessful&&response!!.body().getAsJsonObject().has("data")) {
                    val responseJsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray()
                    facts = Gson().fromJson<List<Fact>>(responseJsonArray, object : TypeToken<List<Fact>>() {}.type)
                    loadCatFactsCallback.onFactsLoaded(facts)
                }else{
                    loadCatFactsCallback.onResponseError(response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                if (t is IOException) {
                    loadCatFactsCallback.onNoConnection()
                } else {
                    loadCatFactsCallback.onTimeOut()
                }
            }
        })
    }

}