package vyvital.mymovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieList implements Parcelable {

    private List<Movie> movieList;

    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    protected MovieList(Parcel in) {
        movieList = in.createTypedArrayList(Movie.CREATOR);
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static final Creator<MovieList> CREATOR = new Creator<MovieList>() {
        @Override
        public MovieList createFromParcel(Parcel in) {
            return new MovieList(in);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };

    public List<Movie> getMovieList() {
        return movieList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(movieList);
    }
}
