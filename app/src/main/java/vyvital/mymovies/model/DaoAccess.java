package vyvital.mymovies.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertOnlySingleMovie (Movie movies);

    @Insert
    void insertMultipleMovies (List<Movie> movieList);

    @Query ("SELECT * FROM movies_db WHERE title = :title")
    Movie fetchOneMoviebyMovieTitle (String title);

    @Query("SELECT * FROM movies_db ORDER BY releaseYear DESC")
    List<Movie> fetchMovies();

    @Query("DELETE FROM movies_db")
    void deleteAll();

    @Update
    void updateMovie (Movie movie);

    @Delete
    void deleteMovie (Movie movie);



}
