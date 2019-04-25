package com.hb.so.di.module.sub;


import com.hb.lib.data.IDataManager;
import com.hb.so.data.DataManager;
import com.hb.so.data.repository.SystemRepository;
import com.hb.so.data.store.system.SystemLocalStorage;
import com.hb.so.data.store.system.SystemRepositoryImpl;
import com.hb.so.data.store.system.SystemStore;
import com.hb.so.di.scope.CustomScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by buihai on 8/8/17.
 */
@Module
public class SystemModule {


    @Provides
    @CustomScope
    SystemStore.LocalStorage provideLocalStorage(IDataManager dm) {
        return new SystemLocalStorage((DataManager) dm);
    }

    @Provides
    @CustomScope
    SystemStore.RequestService provideRequestService(Retrofit retrofit) {
        return retrofit.create(SystemStore.RequestService.class);
    }

    @Provides
    @CustomScope
    SystemRepository provideRepository(SystemStore.LocalStorage storage,
                                       SystemStore.RequestService service) {
        return new SystemRepositoryImpl(storage, service);
    }
}
