package com.hb.so.data.api.response

import com.google.gson.annotations.SerializedName

class DataResponse<D> {
    @SerializedName("items")
    var data: List<D>? = null
    @SerializedName("has_more")
    var hasMore: Boolean = true
}