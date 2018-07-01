package io.github.nenepadi.djorna;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DjornaViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private String mEmail;

    DjornaViewModelFactory(Application application, @Nullable String email){
        mApplication = application;
        mEmail = email;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DjornaViewModel(mApplication, mEmail);
    }
}
