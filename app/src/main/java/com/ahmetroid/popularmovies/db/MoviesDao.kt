package com.ahmetroid.popularmovies.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmetroid.popularmovies.data.model.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getAll(): DataSource.Factory<Int, Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteAll()

}