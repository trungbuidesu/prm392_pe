package com.fptu.prm392;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Jobs")
public class Job {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "job_id")
    private String id;
    @ColumnInfo(name = "job_name")
    private String name;
    @ColumnInfo(name = "job_status")
    private String status;
    @ColumnInfo(name = "job_desc")
    private String description;

    public Job(String id, String name, String status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
