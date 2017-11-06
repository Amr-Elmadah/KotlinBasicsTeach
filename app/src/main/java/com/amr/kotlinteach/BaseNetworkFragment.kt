package com.amr.kotlinteach

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment

/**
 * Created by Amr El-Madah on 11/6/2017.
 */

open class BaseNetworkFragment : Fragment(), NetworkView {

    private var mSnackBarNoConnection: Snackbar? = null

    override fun showNoConnection(retryAction: Action) {

        if (retryAction != null) {
            mSnackBarNoConnection = Snackbar.make(view!!, R.string.check_connection, if (retryAction == null) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_INDEFINITE)
            mSnackBarNoConnection!!.setAction(R.string.retry, {
                retryAction.perform()
            })
        } else {
            mSnackBarNoConnection = Snackbar.make(view!!, R.string.check_connection, if (retryAction == null) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG)
        }
        mSnackBarNoConnection!!.show()
    }

    override fun hideNoConnection() {
        if (mSnackBarNoConnection != null && mSnackBarNoConnection!!.isShown) {
            mSnackBarNoConnection!!.dismiss()
        }
    }

    override fun showServerError() {
        Snackbar.make(view!!, R.string.server_error, Snackbar.LENGTH_LONG).show()
    }

    override fun showTimeOut() {
        Snackbar.make(view!!, R.string.timeout, Snackbar.LENGTH_LONG)
                .show()
    }
}
