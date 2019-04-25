package com.hb.so.ui.main

import com.hb.lib.mvp.impl.lce.HBMvpLcePresenter
import com.hb.so.data.DataManager
import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.data.entity.User
import com.hb.so.data.repository.SystemRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainPresenter
@Inject constructor(
    var repository: SystemRepository
) : HBMvpLcePresenter<MainActivity>(), MainContract.Presenter {

    private val mData = ArrayList<DataWrapper<User>>()
    private val mDataTemp = ArrayList<DataWrapper<User>>()

    private val mDataFilter = ArrayList<DataWrapper<User>>()
    private val mBookmarkSet = TreeSet<Double>()

    private var mPage = 1
    private var isLoadNext = true


    override fun loadData(pullToRefresh: Boolean) {
        if (pullToRefresh) {
            mDataTemp.clear()
            mDataTemp.addAll(mData)
            mData.clear()
            if (isViewAttached()) {
                getView().setData(mDataTemp)
            }
            mPage = 1
        }

        if (isViewAttached()) {
            if (mPage == 1) {
                getView().showLoading(pullToRefresh)
            }
        }

        val dm = dataManager<DataManager>()
        val bookmarkSet = dm.getAllBookmarks()
        disposable.clear()
        disposable.addAll(
            repository.getUsers(mPage)
                .doOnNext {
                    if (it.isEmpty()) isLoadNext = false
                }
                .flatMap {
                    Observable.fromIterable(it)
                }
                .doOnNext {
                    val user = it.getData()
                    user.isBookrmarked = false
                    val key = user.userId.toDouble()
                    if (bookmarkSet.contains(key)) {
                        user.isBookrmarked = true
                        mBookmarkSet.add(key)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mData.add(it)
                }, { error ->
                    if (isViewAttached()) {
                        getView().showError(error, pullToRefresh)
                    }
                }, {
                    if (isViewAttached()) {
                        getView().apply {
                            setData(mData)
                            showContent()
                        }
                    }
                })
        )
    }

    override fun destroy() {
        mDataFilter.clear()
        mData.clear()
        mDataTemp.clear()
        super.destroy()
    }

    override fun loadNextPage() {
        if (!isLoadNext) return
        mPage++
        loadData(pullToRefresh = false)
    }

    override fun pause() {
        val dm = dataManager<DataManager>()
        dm.setBookmarks(mBookmarkSet)
        super.pause()
    }

    override fun setUserSelected(user: User) {
        dataManager<DataManager>().setUser(user)
    }

    override fun bookmark(data: DataWrapper<User>) {
        val key = data.getData().userId.toDouble()
        if (mBookmarkSet.contains(key)) {
            mBookmarkSet.remove(key)
            mDataFilter.remove(data)
        } else {
            mBookmarkSet.add(key)
        }
    }

    override fun loadAll() {
        disposable.add(
            Observable.just(mData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        getView().apply {
                            setData(it)
                            showContent()
                        }
                    }
                }
        )
    }

    override fun loadBookmark() {
        mDataFilter.clear()
        disposable.add(
            Observable.just(mData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    Observable.fromIterable(it)
                }
                .filter {
                    it.getData().isBookrmarked
                }
                .subscribe({
                    mDataFilter.add(it)
                }, {

                }, {
                    if (isViewAttached()) {
                        getView().apply {
                            setData(mDataFilter)
                            showContent()
                        }
                    }
                })
        )
    }
}

