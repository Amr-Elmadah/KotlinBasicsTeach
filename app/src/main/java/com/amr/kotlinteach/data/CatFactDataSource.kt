package com.amr.kotlinteach.data

import com.amr.kotlinteach.data.callbacks.LoadCatFactsCallback

/**
 * Created by Amr El-Madah on 11/6/2017.
 */

interface CatFactDataSource {
    fun loadCatFacts(length: Int, loadCatFactsCallback: LoadCatFactsCallback)
}