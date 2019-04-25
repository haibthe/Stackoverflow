package com.hb.so.data.entity.dw

abstract class DataWrapper<T>(
    private val data: T
) {
    abstract fun getTitle(): String
    abstract fun getSubtitle(): String
    abstract fun getDescription(): String
    abstract fun getIcon(): String

    fun getData(): T = data
}


