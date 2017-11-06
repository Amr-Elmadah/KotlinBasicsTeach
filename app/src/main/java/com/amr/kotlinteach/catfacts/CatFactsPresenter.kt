package com.amr.kotlinteach.catfacts

import com.amr.kotlinteach.Action
import com.amr.kotlinteach.data.CatFactRepository
import com.amr.kotlinteach.data.callbacks.LoadCatFactsCallback
import com.amr.kotlinteach.data.models.Fact


/**
 * Created by Amr El-Madah on 11/6/2017.
 */

class CatFactsPresenter(length: Int, catFactsRepository: CatFactRepository, view: CatFactsContract.View) : CatFactsContract.Presenter {


    private val mFactsRepository: CatFactRepository
    private val mView: CatFactsContract.View
    private var mLength = 0

    init {
        mFactsRepository = checkNotNull(catFactsRepository)
        mView = checkNotNull(view)
        mLength = checkNotNull(length)
        mView.setPresenter(this)
    }

    override fun loadCatFacts(length: Int) {
        mView.setLoadingIndicator(true)
        mFactsRepository.loadCatFacts(length, object : LoadCatFactsCallback {
            override fun onFactsLoaded(facts: List<Fact>) {
                if (!mView.isActive) {
                    return
                }
                mView.setLoadingIndicator(false)
                mView.showCatFacts(facts)
            }

            override fun onResponseError(responseCode: Int) {
                if (!mView.isActive) {
                    return
                }
                mView.setLoadingIndicator(false)
                mView.showServerError()
            }

            override fun onNoConnection() {
                if (!mView.isActive) {
                    return
                }

                mView.setLoadingIndicator(false)
                mView.showNoConnection(object : Action {
                    override fun perform() {
                        loadCatFacts(length)
                    }
                })
            }

            override fun onTimeOut() {
                if (!mView.isActive) {
                    return
                }

                mView.setLoadingIndicator(false)
                mView.showTimeOut()
            }
        })
    }

    override fun onCatFactClicked(fact: Fact) {
        mView.showClickedCatFact(fact)
    }

    override fun onCatFactShareClicked(fact: Fact) {
        mView.shareClickedCatFact(fact)
    }

    override fun start() {
        loadCatFacts(mLength)
    }
}
