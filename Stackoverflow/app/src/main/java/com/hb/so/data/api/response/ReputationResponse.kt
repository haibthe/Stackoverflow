package com.hb.so.data.api.response

import com.google.gson.annotations.SerializedName
import com.hb.so.data.entity.Reputation


data class ReputationResponse(
    @SerializedName("items")
    val data: List<Reputation>,
    @SerializedName("has_more")
    val hasMore: Boolean
)