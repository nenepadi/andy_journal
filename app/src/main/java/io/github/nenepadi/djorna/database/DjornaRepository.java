package io.github.nenepadi.djorna.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DjornaRepository {
    private DjornaDao mDjornaDao;
    private LiveData<List<DjornaEntry>> mAllEntries;
    private LiveData<DjornaEntry> mOneEntry;
    private String ACTION_FLAG;


    public DjornaRepository(Application application, String email){
        DjornaDatabase dbase = DjornaDatabase.getDjornaInstance(application);
        mDjornaDao = dbase.djornaDao();
        mAllEntries = mDjornaDao.findEntriesByUser(email);
    }

    public DjornaRepository(Application application, int id){
        DjornaDatabase dbase = DjornaDatabase.getDjornaInstance(application);
        mDjornaDao = dbase.djornaDao();
        mOneEntry = mDjornaDao.findEntryById(id);
    }

    public LiveData<List<DjornaEntry>> getAllEntries(){
        return mAllEntries;
    }

    public LiveData<DjornaEntry> getOneEntry(){ return mOneEntry; }

    public void insertUpdate(DjornaEntry entry){
        new PushAsyncTask(mDjornaDao, ACTION_FLAG).execute(entry);
    }

    public void setAction(String action) {
        this.ACTION_FLAG = action;
    }


    private static class PushAsyncTask extends AsyncTask<DjornaEntry, Void, Void>{
        private DjornaDao mTaskDao;
        private String mAction;

        PushAsyncTask(DjornaDao dao, String action){
            mTaskDao = dao;
            mAction = action;
        }


        @Override
        protected Void doInBackground(final DjornaEntry... djornaEntries) {
            if(mAction.equals("edit")){
                mTaskDao.updateEntry(djornaEntries[0]);
            } else{
                mTaskDao.createEntry(djornaEntries[0]);
            }

            return null;
        }
    }
}
