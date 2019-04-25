package com.hb.so.data.store.system


import com.hb.so.data.entity.Reputation
import com.hb.so.data.entity.User
import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.data.entity.dw.ReputationDataWrapper
import com.hb.so.data.entity.dw.UserDataWrapper
import com.hb.so.data.repository.SystemRepository
import io.reactivex.Observable

/**
 * Created by buihai on 9/9/17.
 */

class SystemRepositoryImpl(
    private val storage: SystemStore.LocalStorage,
    private val service: SystemStore.RequestService
) : SystemRepository {

    override fun getUsers(page: Int): Observable<List<DataWrapper<User>>> {
        return service.getUsers(page = page)
            .map {
                if (it.data.isNotEmpty()) {
                    it.data.map { user ->
                        UserDataWrapper(user)
                    }
                } else {
                    ArrayList()
                }
            }
    }

    override fun getReputation(user: User, page: Int): Observable<List<DataWrapper<Reputation>>> {
        return service.getReputationByUser(user.userId, page)
            .map {
                if (it.data.isNotEmpty()) {
                    it.data.map { reputation ->
                        ReputationDataWrapper(reputation)
                    }
                } else {
                    ArrayList()
                }
            }
    }

    override fun getDataTest(): Observable<List<DataWrapper<*>>> {
        return Observable.create<List<String>> {
            val data = ArrayList<String>()
            for (i in 0..20) {
                data.add("Item $i")
            }
            it.onNext(data)
            it.onComplete()
        }
            .map {
                it.map {
                    object : DataWrapper<String>(it) {
                        override fun getTitle(): String {
                            return getData()
                        }

                        override fun getSubtitle(): String {
                            return getData()
                        }

                        override fun getDescription(): String {
                            return getData()
                        }

                        override fun getIcon(): String {
                            return getData()
                        }
                    }
                }
            }
    }
}
