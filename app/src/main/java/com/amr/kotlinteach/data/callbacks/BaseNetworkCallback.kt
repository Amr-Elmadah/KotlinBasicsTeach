package com.amr.kotlinteach.data.callbacks

/**
 * Created by Amr El-Madah on 11/6/2017.
 */

interface BaseNetworkCallback {

    fun onResponseError(responseCode: Int)

    fun onNoConnection()

    fun onTimeOut()
}
