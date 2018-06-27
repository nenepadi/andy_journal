package io.github.nenepadi.djorna.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DjornaEntry.class}, version = 1, exportSchema = false)
public abstract class DjornaDatabase extends RoomDatabase {

    private static DjornaDatabase INSTANCE;

    public static DjornaDatabase getDjornaInstance(final Context context){
        if (INSTANCE == null){
            synchronized (DjornaDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DjornaDatabase.class, "djorna_db").build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract DjornaDao djornaDao();
}
