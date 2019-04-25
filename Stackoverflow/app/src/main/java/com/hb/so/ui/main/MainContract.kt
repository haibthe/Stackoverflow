package com.hb.so.ui.main

import com.hb.so.data.entity.dw.DataWrapper
import com.hb.so.data.entity.User

interface MainContract {
    interface View {
        fun loadBy(type: Int)
    }

    interface Presenter {
        fun loadNextPage()

        fun setUserSelected(user: User)

        fun bookmark(data: DataWrapper<User>)

        fun loadAll()

        fun loadBookmark()
    }
}