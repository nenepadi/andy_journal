package io.github.nenepadi.djorna.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;

@Database(entities = {DjornaEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DjornaDatabase extends RoomDatabase {

    private static DjornaDatabase INSTANCE;

    public abstract DjornaDao djornaDao();

    public static DjornaDatabase getDjornaInstance(final Context context){
        if (INSTANCE == null){
            synchronized (DjornaDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DjornaDatabase.class, "djorna_db")
                            // Use this to check database ...
                            // .addCallback(sRoomDbaseCb)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDbaseCb = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDjornaAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDjornaAsync extends AsyncTask<Void, Void, Void> {
        private final DjornaDao dao;

        PopulateDjornaAsync(DjornaDatabase instance) {
            dao = instance.djornaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            Date date = new Date();
            DjornaEntry entry = new DjornaEntry("Lorem ipsum nun isntudient dientrum sqfale apenade",
                    "obed.ademang@gmail.com", 2, date);
            dao.createEntry(entry);
            return null;
        }
    }
}
