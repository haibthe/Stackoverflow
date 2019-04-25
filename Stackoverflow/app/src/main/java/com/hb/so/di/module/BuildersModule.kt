package com.hb.so.di.module

import com.hb.so.di.module.sub.SystemModule
import com.hb.so.di.scope.CustomScope
import com.hb.so.ui.reputation.ReputationActivity
import com.hb.so.ui.main.MainActivity
import com.hb.so.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @CustomScope
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeSplashActivity(): SplashActivity

    @CustomScope
    @ContributesAndroidInjector(modules = [SystemModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @CustomScope
    @ContributesAndroidInjector(modules = [SystemModule::class])
    abstract fun contributeDetailActivity(): ReputationActivity
}