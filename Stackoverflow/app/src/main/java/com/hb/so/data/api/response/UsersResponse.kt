package com.hb.so.data.api.response

import com.google.gson.annotations.SerializedName
import com.hb.so.data.entity.User


data class UsersResponse(
    @SerializedName("items")
    val data: List<User>,
    @SerializedName("has_more")
    val hasMore: Boolean
)