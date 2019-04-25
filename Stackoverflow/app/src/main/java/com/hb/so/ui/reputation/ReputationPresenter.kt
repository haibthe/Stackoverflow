package com.hb.so.ui.reputation

import com.hb.lib.mvp.impl.lce.HBMvpLcePresenter
import com.hb.so.data.DataManager
import com.hb.so.data.entity.Reputation
import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.data.repository.SystemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ReputationPresenter
@Inject constructor(
    var repository: SystemRepository
) : HBMvpLcePresenter<ReputationActivity>(), ReputationContract.Presenter {


    private val mData = ArrayList<DataWrapper<Reputation>>()
    private val mDataTemp = ArrayList<DataWrapper<Reputation>>()

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

        val user = dm.getUser()

        disposable.clear()
        disposable.addAll(
            repository.getReputation(user, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mData.addAll(it)
                    if (isViewAttached()) {
                        getView().apply {
                            setData(mData)
                            showContent()
                        }
                    }

                }, { error ->
                    if (isViewAttached()) {
                        getView().showError(error, pullToRefresh)
                    }
                })
        )
    }


    override fun loadNextPage() {
        if (!isLoadNext) return
        mPage++
        loadData(pullToRefresh = false)
    }


}