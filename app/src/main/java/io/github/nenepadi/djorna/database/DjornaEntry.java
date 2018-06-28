package io.github.nenepadi.djorna.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "entry")
public class DjornaEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "details")
    private String mDetails;

    @ColumnInfo(name = "email")
    private String mEmail;

    // Draft => 1, Saved => 2
    @ColumnInfo(name = "status")
    private int mStatus;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public DjornaEntry(String details, String email, int status, Date createdAt){
        this.mDetails = details;
        this.mEmail = email;
        this.mStatus = status;
        this.createdAt = createdAt;
    }

    public DjornaEntry(int id, String details, String email, int status, Date createdAt){
        this.mId = id;
        this.mDetails = details;
        this.mEmail = email;
        this.mStatus = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
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

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
