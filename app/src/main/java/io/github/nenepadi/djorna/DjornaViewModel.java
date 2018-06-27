package io.github.nenepadi.djorna;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;
import io.github.nenepadi.djorna.database.DjornaRepository;

public class DjornaViewModel extends AndroidViewModel {
    private DjornaRepository mRepository;
    private LiveData<List<DjornaEntry>> mEntries;

    public DjornaViewModel(Application application) {
        super(application);
        mRepository = new DjornaRepository(application);
        mEntries = mRepository.getAllEntries();
    }

    LiveData<List<DjornaEntry>> getAllEntries() {
        return mEntries;
    }

    public void insert(DjornaEntry entry){ mRepository.insert(entry); }
}
