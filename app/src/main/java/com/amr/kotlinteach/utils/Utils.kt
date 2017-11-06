package com.amr.kotlinteach.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.amr.kotlinteach.R


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

object Utils {
    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if `reference` is null
     */
    fun <T> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }

    fun getAppVersion(context: Context): String {
        var pInfo: PackageInfo? = null
        var appVersion = context.getResources().getString(R.string.app_name) + "_"
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
            appVersion += pInfo!!.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return appVersion
    }

    fun <T> listEqualsNoOrder(l1: List<T>, l2: List<T>): Boolean {
        val s1 = HashSet(l1)
        val s2 = HashSet(l2)

        return s1 == s2
    }

}
