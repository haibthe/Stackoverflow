package com.hb.so.ui.reputation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.hb.lib.mvp.impl.lce.sr.HBMvpLceSRActivity
import com.hb.so.R
import com.hb.so.data.entity.Reputation
import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.ui.base.LoadingViewHolder
import com.hb.uiwidget.recyclerview.BaseAdapter
import com.hb.uiwidget.recyclerview.BaseViewHolder
import com.hb.uiwidget.recyclerview.EndlessRecyclerViewListener


class ReputationActivity : HBMvpLceSRActivity<List<DataWrapper<Reputation>>,ReputationPresenter>(), ReputationContract.View {


    override fun getResLayoutId(): Int {
        return R.layout.activity_lce_sr_search
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)

            actionBar.title = "Reputation"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setupRecylcerView(addItemDecoration: Boolean) {
        super.setupRecylcerView(true)
        val rv = getRecyclerView()
        rv.addOnScrollListener(object : EndlessRecyclerViewListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                mPresenter.loadNextPage()
            }
        })
    }

    override fun createAdapter(context: Context, recyclerView: RecyclerView): RecyclerView.Adapter<*> {
        val adapter = ReputationAdapter(context, recyclerView)
        return adapter
    }

    override fun setData(data: List<DataWrapper<Reputation>>) {
        val adapter = getAdapter<ReputationAdapter>()
        adapter.data = data
    }

    class ReputationViewHolder(itemView: View) : BaseViewHolder<DataWrapper<Reputation>>(itemView) {

        @BindView(R.id.tv_type)
        lateinit var type: TextView
        @BindView(R.id.tv_change)
        lateinit var change: TextView
        @BindView(R.id.tv_post_id)
        lateinit var postId: TextView
        @BindView(R.id.tv_create_date)
        lateinit var createDate: TextView

        override fun bindData(data: DataWrapper<Reputation>) {

            type.text = data.getTitle()
            change.text = data.getSubtitle()
            postId.text = data.getDescription()
            createDate.text = data.getIcon()
        }
    }

    class ReputationAdapter(
        context: Context, recyclerView: RecyclerView
    ) : BaseAdapter<List<DataWrapper<Reputation>>, BaseViewHolder<*>>(context, recyclerView) {

        companion object {
            const val VT_LOADING = -1
            const val VT_NORMAL = 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
           return when (viewType) {
                VT_NORMAL -> {
                    val itemView = mInflater.inflate(R.layout.itemview_reputation, parent, false)
                    ReputationViewHolder(itemView)
                }
                else -> {
                    val itemView = mInflater.inflate(R.layout.itemview_loading, parent, false)
                    LoadingViewHolder(itemView)
                }
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
            if (holder is ReputationViewHolder) {
                super.onBindViewHolder(holder, position)
                val data = getItem<DataWrapper<Reputation>>(position)!!
                holder.bindData(data)
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position < mData!!.size) {
                return VT_NORMAL
            }
            return VT_LOADING
        }

        override fun getItemCount(): Int {
            if (mData == null)
                return 0
            val size = mData!!.size
            if (size == 0) return 0
            return size + 1
        }
    }
}