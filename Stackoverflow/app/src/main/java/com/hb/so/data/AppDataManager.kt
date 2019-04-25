package com.hb.so.data

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.hb.so.data.cache.ICache
import com.hb.so.data.entity.User
import com.hb.so.data.pref.PreferenceHelper
import java.util.*


class AppDataManager
constructor(
    private val context: Context,
    private val pref: PreferenceHelper,
    private val cache: ICache
) : DataManager {

    companion object {
        const val BOOKMARK_TAG = "BOOKMARK"
    }

    private var mUser: User? = null


    override fun setUser(data: User) {
        mUser = data
    }

    override fun getUser(): User {
        return mUser!!
    }

    override fun setBookmarks(data: TreeSet<Double>) {
        cache.delete(BOOKMARK_TAG)
        cache.setObject(BOOKMARK_TAG, data)
    }

    override fun getAllBookmarks(): TreeSet<Double> {
        var data = cache.getObject<TreeSet<Double>>(BOOKMARK_TAG, object : TypeToken<TreeSet<Double>>() {}.rawType)
        if (data == null) data = TreeSet()
        return data
    }
}