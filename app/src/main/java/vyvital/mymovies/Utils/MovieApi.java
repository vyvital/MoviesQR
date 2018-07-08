package vyvital.mymovies.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vyvital.mymovies.model.Movie;

public interface MovieApi {

    @GET("/json/movies.json")
    Call<List<Movie>> getJson();
}
