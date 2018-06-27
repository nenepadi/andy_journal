package io.github.nenepadi.djorna.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DjornaDao {
    @Query("SELECT * FROM entry ORDER BY penned_date DESC")
    LiveData<List<DjornaEntry>> findAllEntries();

    @Query("SELECT * FROM entry WHERE id = :id")
    LiveData<DjornaEntry> findEntryById(int id);

    @Insert
    void createEntry(DjornaEntry entry);

    @Delete
    void removeEntry(DjornaEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(DjornaEntry entry);
}
