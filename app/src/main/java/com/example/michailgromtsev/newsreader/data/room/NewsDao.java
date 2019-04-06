package com.example.michailgromtsev.newsreader.data.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news ORDER BY publishDate DESC")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM news WHERE section = :section ORDER BY publishDate DESC")
    Observable<List<NewsEntity>> getAllBySection(String section);

    @Query("SELECT * FROM news WHERE id = :id")
    NewsEntity getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

    @Query("DELETE FROM news")
    void deleteAll();
}
