package com.hb.so.data.entity.dw

import com.hb.so.common.AppConstants
import com.hb.so.data.entity.Reputation


class ReputationDataWrapper(data: Reputation) : DataWrapper<Reputation>(data) {

    private var _rep = getData()

    override fun getTitle(): String {
        return _rep.reputationHistoryType
    }

    override fun getSubtitle(): String {
        return "${_rep.reputationChange}"
    }

    override fun getDescription(): String {
        return "${_rep.postId}"
    }

    override fun getIcon(): String {
        return AppConstants.formatDate.format(_rep.creationDate * 1000L)
    }
}