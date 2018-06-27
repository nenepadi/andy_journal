package io.github.nenepadi.djorna.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "entry")
public class DjornaEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "detail")
    private String mDetails;

    @NonNull
    @ColumnInfo(name = "penned_date")
    private Date mDate;

    @Ignore
    public DjornaEntry(String title, String details, Date date){
        this.mTitle = title;
        this.mDetails = details;
        this.mDate = date;
    }

    public DjornaEntry(int id, String title, String details, Date date){
        this.mId = id;
        this.mTitle = title;
        this.mDetails = details;
        this.mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }
}
