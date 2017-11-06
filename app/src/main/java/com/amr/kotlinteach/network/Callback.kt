package com.amr.kotlinteach.network

import com.amr.kotlinteach.data.callbacks.BaseNetworkCallback
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by Amr El-Madah on 11/6/2017.
 */
abstract class Callback<T> : retrofit2.Callback<T> {

    private var mNetworkCallback: BaseNetworkCallback? = null

    constructor() {}

    constructor(callback: BaseNetworkCallback) {
        mNetworkCallback = checkNotNull(callback)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful()) {
            onResponseSuccess(call, response)
        } else {
            onResponseFailure(call, response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Timber.w(t, "onFailure()")
        if (t is SocketTimeoutException) {
            Timber.w("Timeout")
            onTimeOut(call, t)
        } else if (t is IOException) {
            Timber.w("No connection")
            onNoConnection(call, t)
        } else {
            Timber.w("Another Failure")
            onNoConnection(call, t)
        }
    }

    abstract fun onResponseSuccess(call: Call<T>, response: Response<T>)

    fun onResponseFailure(call: Call<T>, response: Response<T>) {
        mNetworkCallback?.onResponseError(response.code())
    }

    fun onNoConnection(call: Call<T>, t: Throwable) {
        mNetworkCallback?.onNoConnection()
    }

    fun onTimeOut(call: Call<T>, t: Throwable) {
        mNetworkCallback?.onTimeOut()
    }
}
