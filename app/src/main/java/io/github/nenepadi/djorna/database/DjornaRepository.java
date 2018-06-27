package io.github.nenepadi.djorna.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DjornaRepository {
    private DjornaDao mDjornaDao;
    private LiveData<List<DjornaEntry>> mAllEntries;

    public DjornaRepository(Application application){
        DjornaDatabase dbase = DjornaDatabase.getDjornaInstance(application);
        mDjornaDao = dbase.djornaDao();
        mAllEntries = mDjornaDao.findAllEntries();
    }

    public LiveData<List<DjornaEntry>> getAllEntries(){
        return mAllEntries;
    }

    public void insert(DjornaEntry entry){
        new PushAsyncTask(mDjornaDao).execute(entry);
    }

    private static class PushAsyncTask extends AsyncTask<DjornaEntry, Void, Void>{
        private DjornaDao mTaskDao;

        PushAsyncTask(DjornaDao dao){
            mTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final DjornaEntry... djornaEntries) {
            mTaskDao.createEntry(djornaEntries[0]);
            return null;
        }
    }
}
