package com.amr.kotlinteach.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView


/**
 * Created by Amr El-Madah on 11/6/2017.
 */
class DividerItemDecoration : RecyclerView.ItemDecoration {

    private var mDivider: Drawable? = null

    /**
     * Default divider will be used
     */
    constructor(context: Context) {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        mDivider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    /**
     * Custom divider will be used
     */
    constructor(context: Context, @DrawableRes resId: Int) {
        mDivider = ContextCompat.getDrawable(context, resId)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.getIntrinsicHeight()

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}