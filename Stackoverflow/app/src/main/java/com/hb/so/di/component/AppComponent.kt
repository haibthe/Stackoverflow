package com.hb.so.di.component

import android.content.Context
import com.hb.lib.data.IDataManager
import com.hb.lib.utils.RxBus
import com.hb.so.app.App
import com.hb.so.di.module.AppModule
import com.hb.so.di.module.BuildersModule
import com.hb.so.di.module.NetModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        BuildersModule::class,
        AppModule::class,
        NetModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)

    fun bus(): RxBus

    fun dataManager(): IDataManager

    fun context(): Context

    fun retrofit(): Retrofit

}