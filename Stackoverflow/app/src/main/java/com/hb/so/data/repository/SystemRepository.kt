package com.hb.so.data.repository

import com.hb.so.data.entity.Reputation
import com.hb.so.data.entity.User
import com.hb.so.data.entity.dw.DataWrapper
import io.reactivex.Observable

/**
 * Created by buihai on 9/9/17.
 */

interface SystemRepository {

    fun getDataTest(): Observable<List<DataWrapper<*>>>

    fun getUsers(page: Int = 1) : Observable<List<DataWrapper<User>>>

    fun getReputation(user: User, page: Int = 1): Observable<List<DataWrapper<Reputation>>>
}
