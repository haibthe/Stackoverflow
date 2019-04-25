package com.hb.so.data.entity.dw

import com.hb.so.data.entity.User

class UserDataWrapper(user: User): DataWrapper<User>(user) {

    var _user = getData()

    override fun getTitle(): String {
        return _user.displayName
    }

    override fun getSubtitle(): String {
        return _user.reputation.toString()
    }

    override fun getDescription(): String {
        return _user.location
    }

    override fun getIcon(): String {
        return _user.profileImage
    }
}