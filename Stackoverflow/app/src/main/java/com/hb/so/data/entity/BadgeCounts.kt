package com.hb.so.data.entity

import com.google.gson.annotations.SerializedName

data class BadgeCounts(
    @SerializedName("bronze")
    val bronze: Int,
    @SerializedName("gold")
    val gold: Int,
    @SerializedName("silver")
    val silver: Int
)