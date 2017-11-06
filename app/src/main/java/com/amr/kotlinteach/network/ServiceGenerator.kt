package com.amr.kotlinteach.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

object ServiceGenerator {

    private val TIMEOUT_IN_SECONDS = 30

    private val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    // Deserialize Date as long
    val gsonInstance = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                @Throws(JsonParseException::class)
                override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
                    return Date(json.asJsonPrimitive.asLong)
                }
            })
            .create()

    private val builder = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL_API)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonInstance))

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}