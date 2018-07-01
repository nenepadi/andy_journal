package io.github.nenepadi.djorna;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import io.github.nenepadi.djorna.database.DjornaEntry;
import io.github.nenepadi.djorna.database.DjornaRepository;

public class DjornaDetailModel extends ViewModel {
    private DjornaRepository mRepository;
    private LiveData<DjornaEntry> mEntry;

    DjornaDetailModel(Application application, int id) {
        mRepository = new DjornaRepository(application, id);
        mRepository.setAction("edit");
        mEntry = mRepository.getOneEntry();
    }

    LiveData<DjornaEntry> getEntry() { return mEntry; }

    public void update(DjornaEntry entry){ mRepository.insertUpdate(entry); }
}
