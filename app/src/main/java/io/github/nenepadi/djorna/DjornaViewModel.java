package io.github.nenepadi.djorna;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;
import io.github.nenepadi.djorna.database.DjornaRepository;

public class DjornaViewModel extends ViewModel {
    private DjornaRepository mRepository;
    private LiveData<List<DjornaEntry>> mEntries;

    DjornaViewModel(Application application, String email) {
        mRepository = new DjornaRepository(application, email);
        mRepository.setAction("save");
        mEntries = mRepository.getAllEntries();
    }

    LiveData<List<DjornaEntry>> getAllEntries() { return mEntries; }

    public void insert(DjornaEntry entry){ mRepository.insertUpdate(entry); }
}
