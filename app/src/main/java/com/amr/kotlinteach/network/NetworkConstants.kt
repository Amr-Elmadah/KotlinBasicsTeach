package com.amr.kotlinteach.network

import com.amr.kotlinteach.BuildConfig

/**
 * Created by Amr El-Madah on 11/6/2017.
 */

object NetworkConstants {

    val QA = BuildConfig.QA

    /**
     * The base url of the web service.
     */

    val BASE_URL = "https://catfact.ninja"

    //We use that for QA server for debugging - also we make favor for production and staging (check gradle)
    val BASE_URL_QA = "https://catfact.ninja"

    var BASE_URL_API = baseUrl + "/"

    val baseUrl: String
        get() = if (QA) {
            BASE_URL_QA
        } else {
            BASE_URL
        }
}