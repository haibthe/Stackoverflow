package com.hb.so.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.android.material.tabs.TabLayout
import com.hb.lib.mvp.impl.lce.sr.HBMvpLceSRActivity
import com.hb.so.R
import com.hb.so.app.App
import com.hb.so.common.AppConstants
import com.hb.so.data.entity.User
import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.navigation.Navigator
import com.hb.so.ui.base.LoadingViewHolder
import com.hb.uiwidget.recyclerview.BaseAdapter
import com.hb.uiwidget.recyclerview.BaseViewHolder
import com.hb.uiwidget.recyclerview.EndlessRecyclerViewListener
import com.hb.uiwidget.recyclerview.OnItemClickListener

class MainActivity : HBMvpLceSRActivity<List<DataWrapper<User>>, MainPresenter>(), MainContract.View {

    override fun getResLayoutId(): Int {
        return R.layout.activity_main
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.tablayout)
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        mLceViewHolder.contentView.isEnabled = false

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                loadBy(tab.position)
            }
        })
    }

    companion object {
        const val LOAD_ALL = 0
        const val LOAD_BY_BOOKMARK = 1
    }

    var mLoadType = LOAD_ALL

    override fun loadBy(type: Int) {
        mLoadType = type

        if (type == LOAD_ALL) {
            mPresenter.loadAll()
        } else {
            mPresenter.loadBookmark()
        }

    }

    override fun setupRecylcerView(addItemDecoration: Boolean) {
        super.setupRecylcerView(false)
        val rv = getRecyclerView()
        rv.addOnScrollListener(object : EndlessRecyclerViewListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (mLoadType == LOAD_ALL) {
                    mPresenter.loadNextPage()
                }
            }
        })
    }

    override fun createAdapter(context: Context, recyclerView: RecyclerView): RecyclerView.Adapter<*> {
        val adapter = MyAdapter(context, recyclerView)
        adapter.setOnItemClickListener(OnItemClickListener { anchor, obj, position ->
            if (obj is DataWrapper<*>) {
                mPresenter.setUserSelected(obj.getData() as User)
            }
            Navigator.startDetail(this)
        })
        adapter.setOnBookmarkListener(object : MyAdapter.OnBookmarkListener {
            override fun onBookmark(user: DataWrapper<User>, position: Int) {
                mPresenter.bookmark(user)
                if (mLoadType == LOAD_BY_BOOKMARK) {
                    adapter.notifyItemRemoved(position)
                }
            }
        })
        return adapter
    }

    override fun setData(data: List<DataWrapper<User>>) {
        val adapter = getAdapter<MyAdapter>()
        adapter.setLoadType(mLoadType)
        adapter.data = data
    }

    class MyViewHolder(itemView: View) : BaseViewHolder<DataWrapper<*>>(itemView) {

        @BindView(R.id.tv_display_name)
        lateinit var title: TextView
        @BindView(R.id.tv_reputation)
        lateinit var reputation: TextView
        @BindView(R.id.tv_location)
        lateinit var location: TextView
        @BindView(R.id.tv_last_access_date)
        lateinit var lastAccessDate: TextView

        @BindView(R.id.image_view_avatar)
        lateinit var avatar: ImageView

        @BindView(R.id.iv_bookmark)
        lateinit var bookmark: ImageView

        override fun bindData(data: DataWrapper<*>) {
            title.text = data.getTitle()
            reputation.text = data.getSubtitle()
            location.text = data.getDescription()
            App.imageHelper.loadAvatar(avatar, data.getIcon())

            val user = data.getData() as User
            lastAccessDate.text = AppConstants.formatDate.format(user.lastAccessDate * 1000L)

            val resId = if (user.isBookrmarked) {
                R.drawable.ic_bookmark_black_24dp
            } else {
                R.drawable.ic_debookmark_black_24dp
            }
            bookmark.setImageResource(resId)
        }
    }




    class MyAdapter(
        context: Context, rv: RecyclerView
    ) : BaseAdapter<List<DataWrapper<User>>, BaseViewHolder<*>>(context, rv) {

        companion object {
            const val VT_LOADING = -1
            const val VT_NORMAL = 0
        }

        private var mOnBookmarkListener: OnBookmarkListener? = null
        private var mLoadType: Int = LOAD_ALL

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
            return when (viewType) {
                VT_NORMAL -> {
                    val itemView = mInflater.inflate(R.layout.itemview_user, parent, false)
                    MyViewHolder(itemView)
                }
                else -> {
                    val itemView = mInflater.inflate(R.layout.itemview_loading, parent, false)
                    LoadingViewHolder(itemView)
                }
            }

        }

        override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
            if (holder is MyViewHolder) {
                super.onBindViewHolder(holder, position)
                val data = getItem<DataWrapper<User>>(position)!!
                holder.bindData(data)

                holder.bookmark.setOnClickListener {
                    val user = data.getData()
                    user.isBookrmarked = !user.isBookrmarked
                    mOnBookmarkListener?.onBookmark(data, position)
                    notifyItemChanged(position)
                }
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
            if (size == 0) {
                return 0
            }
            if (mLoadType == LOAD_ALL)
                return size + 1
            return size
        }

        fun setOnBookmarkListener(listener: OnBookmarkListener) {
            mOnBookmarkListener = listener
        }

        fun setLoadType(type: Int) {
            mLoadType = type
        }

        interface OnBookmarkListener {
            fun onBookmark(user: DataWrapper<User>, position: Int)
        }
    }
}
