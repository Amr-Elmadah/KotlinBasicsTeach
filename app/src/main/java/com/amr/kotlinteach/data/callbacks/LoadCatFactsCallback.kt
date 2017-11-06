package com.amr.kotlinteach.data.callbacks

import com.amr.kotlinteach.data.models.Fact

/**
 * Created by Amr El-Madah on 11/6/2017.
 */

interface LoadCatFactsCallback : BaseNetworkCallback {
    fun onFactsLoaded(facts: List<Fact>)
}