package com.hb.so.data.store.system

import com.hb.so.data.api.response.ReputationResponse
import com.hb.so.data.api.response.UsersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by buihai on 9/9/17.
 */

interface SystemStore {


    interface LocalStorage {
    }

    interface RequestService {

        @GET("users")
        fun getUsers(
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int = 30,
            @Query("site") site: String = "stackoverflow"
        ): Observable<UsersResponse>

        @GET("users/{USER_ID}/reputation-history")
        fun getReputationByUser(
            @Path("USER_ID") userId: Int,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int = 30,
            @Query("site") site: String = "stackoverflow"
        ): Observable<ReputationResponse>
    }
}
