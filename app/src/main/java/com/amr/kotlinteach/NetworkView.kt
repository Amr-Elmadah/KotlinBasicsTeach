package com.amr.kotlinteach

/**
 * Created by Amr El-Madah on 11/6/2017.
 */
/**
 * Created by Amr ElMadah on 10/31/17.
 */
interface NetworkView {

    fun showNoConnection(retryAction: Action)

    fun hideNoConnection()

    fun showServerError()

    fun showTimeOut()
}
