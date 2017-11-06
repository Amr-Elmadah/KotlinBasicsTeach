package com.amr.kotlinteach.catfacts

import com.amr.kotlinteach.data.models.Fact
import com.amr.kotlinteach.BasePresenter
import com.amr.kotlinteach.NetworkView
import com.amr.kotlinteach.BaseView



/**
 * Created by Amr El-Madah on 11/6/2017.
 */

interface CatFactsContract {

    interface View : BaseView<Presenter>, NetworkView {

        val isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showCatFacts(catFacts: List<Fact>)

        fun showClickedCatFact(fact: Fact)

        fun shareClickedCatFact(fact: Fact)
    }

    interface Presenter : BasePresenter {

        fun loadCatFacts(length: Int)

        fun onCatFactClicked(fact: Fact)

        fun onCatFactShareClicked(fact: Fact)
    }
}
