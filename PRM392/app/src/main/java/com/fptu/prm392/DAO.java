package com.fptu.prm392;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Job job);

    @Update
    Integer update(Job job);

    @Delete
    void delete(Job job);

    @Query("SELECT * FROM Jobs " +
            "WHERE (:id IS NULL OR job_id LIKE :id) " +
            "AND (:name IS NULL OR job_name LIKE :name)")
    List<Job> getJobs(@Nullable String id, @Nullable String name);

    @Query("SELECT * FROM JOBS " +
            "WHERE job_id LIKE :id")
    Job getJobById(String id);
}
