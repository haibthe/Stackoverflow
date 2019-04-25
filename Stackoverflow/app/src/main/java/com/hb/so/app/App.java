package com.hb.so.app;


import com.hb.lib.app.HBMvpApp;
import com.hb.so.BuildConfig;
import com.hb.so.di.component.AppComponent;
import com.hb.so.di.component.DaggerAppComponent;
import com.hb.so.di.module.AppModule;
import com.hb.so.utils.image.GlideImageHelper;
import com.hb.so.utils.image.ImageHelper;
import timber.log.Timber;

public class App extends HBMvpApp {

    public static ImageHelper imageHelper;

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected void init() {


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initAllComponent();

        imageHelper = new GlideImageHelper(getBaseContext());
    }

    public void initAllComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
