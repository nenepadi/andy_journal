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

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "detail")
    private String mDetails;

    @ColumnInfo(name = "email")
    private String mEmail;

    // Draft => 1, Saved => 2
    @ColumnInfo(name = "status")
    private int mStatus;

    @ColumnInfo(name = "penned_date")
    private Date mDate;

    @Ignore
    public DjornaEntry(String title, String details, String email, int status, @NonNull Date date){
        this.mTitle = title;
        this.mDetails = details;
        this.mDate = date;
        this.mEmail = email;
        this.mStatus = status;
    }

    public DjornaEntry(int id, String title, String details, String email, int status, @NonNull Date date){
        this.mId = id;
        this.mTitle = title;
        this.mDetails = details;
        this.mDate = date;
        this.mEmail = email;
        this.mStatus = status;
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

    public void setDate(@NonNull Date date) {
        this.mDate = date;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
