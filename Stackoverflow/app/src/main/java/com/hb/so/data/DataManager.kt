package com.hb.so.data

import com.hb.lib.data.IDataManager
import com.hb.so.data.entity.User
import com.hb.so.data.pref.PreferenceHelper
import java.util.*

interface DataManager : IDataManager, PreferenceHelper {

    fun setUser(data: User)

    fun getUser(): User

    fun setBookmarks(data: TreeSet<Double>)

    fun getAllBookmarks(): TreeSet<Double>
}