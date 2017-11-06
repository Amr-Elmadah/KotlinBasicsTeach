package com.amr.kotlinteach.catfacts

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.amr.kotlinteach.BaseNetworkFragment
import com.amr.kotlinteach.R
import com.amr.kotlinteach.customviews.DividerItemDecoration
import com.amr.kotlinteach.customviews.OnVerticalScrollListener
import com.amr.kotlinteach.customviews.RecyclerViewEmptySupport
import com.amr.kotlinteach.data.CatFactRepository
import com.amr.kotlinteach.data.models.Fact
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter


class CatFactsFragment : BaseNetworkFragment(), CatFactsContract.View {
    private var rvCatFacts: RecyclerViewEmptySupport? = null
    private var srRecyclerView: SwipeRefreshLayout? = null
    private var srEmptyView: SwipeRefreshLayout? = null
    private var tvMaxLength: TextView? = null
    private var sbMaxLength: SeekBar? = null
    private var llLength: LinearLayout? = null
    private var mCatFactsAdapter: CatFactsAdapter? = null

    private var mPresenter: CatFactsContract.Presenter? = null

    private var mLength = 200
    private val sbStepSize = 10

    override val isActive: Boolean
        get() = isAdded


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mCatFactsAdapter = CatFactsAdapter(ArrayList(), object : CatFactsAdapter.OnItemClickListener {
            override fun onItemClick(fact: Fact) {
                mPresenter!!.onCatFactClicked(fact)
            }

            override fun onItemShareClick(fact: Fact) {
                mPresenter!!.onCatFactShareClicked(fact)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater!!.inflate(R.layout.fragment_cat_facts, container, false)

        rvCatFacts = mView.findViewById<RecyclerViewEmptySupport>(R.id.rv_cat_facts)
        srRecyclerView = mView.findViewById<SwipeRefreshLayout>(R.id.srl_cat_facts)
        srEmptyView = mView.findViewById<SwipeRefreshLayout>(R.id.srl_cat_facts_emptyView)

        tvMaxLength = mView.findViewById<TextView>(R.id.tv_max_length)
        sbMaxLength = mView.findViewById<SeekBar>(R.id.sb_length)
        llLength = mView.findViewById<LinearLayout>(R.id.ll_length)



        setupRecyclerView(rvCatFacts)
        setupSwipeRefreshLayout(srRecyclerView)
        setupSwipeRefreshLayout(srEmptyView)
        initLengthSeekBar()
        if (mPresenter == null) {
            mPresenter = CatFactsPresenter(mLength, CatFactRepository(), this)
        }

        if (savedInstanceState == null) {
            mPresenter!!.start()
        }

        return mView
    }

    private fun initLengthSeekBar() {
        sbMaxLength!!.progress = mLength
        tvMaxLength!!.text = "" + mLength
        sbMaxLength!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var progress = progress
                //To force the seek bar step size to $sbStepSize
                if (progress % sbStepSize == 0) {
                    mLength = progress
                    mPresenter!!.loadCatFacts(mLength)
                    tvMaxLength!!.text = "" + mLength
                } else {
                    progress = Math.round((progress / sbStepSize).toFloat()) * sbStepSize
                    seekBar.setProgress(progress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    private fun setupSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout?) {
        swipeRefreshLayout!!.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener { mPresenter!!.loadCatFacts(mLength) }
    }

    private fun setupRecyclerView(recyclerView: RecyclerViewEmptySupport?) {
        recyclerView!!.addItemDecoration(DividerItemDecoration(context))
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setEmptyView(srEmptyView!!)
        recyclerView.addOnScrollListener(OnVerticalScrollListener(object : OnVerticalScrollListener.ScrollDirectionCallback {
            override fun onScrolledUp() {
                viewView(llLength)
            }

            override fun onScrolledDown() {
                hideView(llLength)
            }
        }))
        recyclerView.setAdapter(mCatFactsAdapter)
    }


    private fun hideView(view: View?) {
        if (view!!.visibility == View.VISIBLE) {
            val animation = AnimationUtils.loadAnimation(activity, R.anim.slide_top_bottom)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.GONE
                }
            })

            view.startAnimation(animation)
        }
    }

    private fun viewView(view: View?) {
        if (view!!.visibility == View.GONE) {
            val animation = AnimationUtils.loadAnimation(activity, R.anim.slide_bottom_top)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = View.VISIBLE
                }
            })

            view.startAnimation(animation)
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (srRecyclerView != null && srEmptyView != null) {
            srRecyclerView!!.post {
                srRecyclerView!!.isRefreshing = active
                // Set refreshing to false on the empty view's SwipeRefreshLayout if it's active
                if (srEmptyView!!.isRefreshing && !active) {
                    srEmptyView!!.isRefreshing = false
                }
            }
        } else {
            startActivity(Intent(activity, CatFactActivity::class.java))
            activity.finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    override fun showCatFacts(catFacts: List<Fact>) {
        rvCatFacts!!.setLoaded(true)
        mCatFactsAdapter!!.replaceData(catFacts)

    }

    override fun showClickedCatFact(fact: Fact) {
        Toast.makeText(activity, fact.fact, Toast.LENGTH_SHORT).show()
    }

    override fun shareClickedCatFact(fact: Fact) {
        if (fact != null && !fact.fact.isEmpty()) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, fact.fact)
            startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.share_using)))
        } else {
            Toast.makeText(activity, resources.getString(R.string.no_cat_fact_text), Toast.LENGTH_SHORT).show()
        }
    }


    override fun setPresenter(presenter: CatFactsContract.Presenter) {
        mPresenter = presenter
    }

    class CatFactsAdapter(catFacts: List<Fact>, onItemClickListener: OnItemClickListener) : RecyclerSwipeAdapter<RecyclerView.ViewHolder>() {

        private var mCatFacts: List<Fact>? = null
        private val mOnItemClickListener: OnItemClickListener

        private var isLoading: Boolean = false

        init {
            mOnItemClickListener = checkNotNull(onItemClickListener)
            setList(catFacts)
        }


        private fun setLoaded() {
            isLoading = false
        }

        fun replaceData(catFacts: List<Fact>) {
            setList(catFacts)
            notifyDataSetChanged()
        }

        private fun setList(catFacts: List<Fact>) {
            mCatFacts = checkNotNull(catFacts)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == TYPE_ITEM) {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat_fact, viewGroup, false)
                return ItemViewHolder(view, object : ItemViewHolder.OnItemInteractionListener {
                    override fun onItemClick(position: Int) {
                        mOnItemClickListener.onItemClick(mCatFacts!![position])
                    }

                    override fun onItemShareClick(position: Int) {
                        mOnItemClickListener.onItemShareClick(mCatFacts!![position])
                    }
                })
            } else {
                throw IllegalStateException("Not a valid type")
            }
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            if (viewHolder is ItemViewHolder) {
                val fact = mCatFacts!![position]
                viewHolder.catFactName!!.text = fact.fact
            }
        }

        override fun getItemViewType(position: Int): Int {
            return TYPE_ITEM
        }

        override fun getItemCount(): Int {
            return if (null != mCatFacts) mCatFacts!!.size else 0
        }

        override fun getSwipeLayoutResourceId(position: Int): Int {
            return R.id.swipe

        }

        class ItemViewHolder(view: View, val mOnItemInteractionListener: OnItemInteractionListener) : RecyclerView.ViewHolder(view) {

            var catFactName: TextView
            var llListItem: LinearLayout
            var llShare: LinearLayout

            init {
                catFactName = view.findViewById<TextView>(R.id.tv_cat_fact_title)
                llListItem = view.findViewById<LinearLayout>(R.id.ll_list_item)
                llListItem.setOnClickListener {
                    mOnItemInteractionListener.onItemClick(adapterPosition)
                }
                llShare = view.findViewById<LinearLayout>(R.id.ll_share)
                llShare.setOnClickListener {
                    mOnItemInteractionListener.onItemShareClick(adapterPosition)

                }
            }

            interface OnItemInteractionListener {
                fun onItemClick(position: Int)

                fun onItemShareClick(position: Int)
            }
        }

        interface OnItemClickListener {
            fun onItemClick(fact: Fact)

            fun onItemShareClick(fact: Fact)
        }

        companion object {
            private val TYPE_ITEM = 1
        }
    }

    companion object {

        fun newInstance(): CatFactsFragment {
            val notificationsFragment = CatFactsFragment()
            val args = Bundle()
            notificationsFragment.arguments = args
            return notificationsFragment
        }
    }
}// Required empty public constructor