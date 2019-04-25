package com.hb.so.data.entity

import com.google.gson.annotations.SerializedName

data class Reputation(
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("reputation_change")
    val reputationChange: Int,
    @SerializedName("reputation_history_type")
    val reputationHistoryType: String,
    @SerializedName("user_id")
    val userId: Int
)