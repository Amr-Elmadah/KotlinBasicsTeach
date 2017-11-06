package com.amr.kotlinteach.network

import android.content.Context
import android.net.ConnectivityManager


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

object NetworkChecker {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()
    }

}