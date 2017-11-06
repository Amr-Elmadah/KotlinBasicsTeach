package com.amr.kotlinteach.customviews

import android.support.v7.widget.RecyclerView



/**
 * Created by Amr El-Madah on 11/6/2017.
 */
class OnVerticalScrollListener(private val mScrollDirectionCallback: ScrollDirectionCallback) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (dy < 0) {
            mScrollDirectionCallback.onScrolledUp()
        } else if (dy > 0) {
            mScrollDirectionCallback.onScrolledDown()
        }
    }

    interface ScrollDirectionCallback {
        fun onScrolledUp()

        fun onScrolledDown()
    }
}
