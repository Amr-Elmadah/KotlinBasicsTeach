package com.amr.kotlinteach.customviews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

class RecyclerViewEmptySupport : RecyclerView {

    private var emptyView: View? = null
    private var loaded = false

    private val emptyObserver = object : AdapterDataObserver() {


        override fun onChanged() {
            val adapter = adapter
            if (adapter != null && emptyView != null && loaded) {
                if (adapter.itemCount == 0) {
                    emptyView!!.setVisibility(View.VISIBLE)
                    this@RecyclerViewEmptySupport.visibility = View.GONE
                } else {
                    emptyView!!.setVisibility(View.GONE)
                    this@RecyclerViewEmptySupport.visibility = View.VISIBLE
                }
            }

        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(emptyObserver)

        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
    }

    fun setLoaded(loaded: Boolean) {
        this.loaded = loaded
    }
}