package io.github.nenepadi.djorna;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class DjornaDetailModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private int mId;

    DjornaDetailModelFactory(Application application, int id){
        mApplication = application;
        mId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DjornaDetailModel(mApplication, mId);
    }
}
