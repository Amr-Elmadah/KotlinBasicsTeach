package com.amr.kotlinteach.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

object ActivityUtils {

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.
     */
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        //        transaction.setCustomAnimations(R.anim.activity_open_translate, 0);
        transaction.add(frameId, fragment)
        transaction.commit()
        //        fragmentManager.executePendingTransactions();
    }

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.
     */
    fun replaceFragmentToActivity(fragmentManager: FragmentManager,
                                  fragment: Fragment, frameId: Int, tag: String) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, tag)
        transaction.commit()
    }

}
