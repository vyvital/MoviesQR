package vyvital.mymovies;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vyvital.mymovies.Utils.MovieApi;
import vyvital.mymovies.model.Movie;
import vyvital.mymovies.model.MovieDatabase;
import vyvital.mymovies.model.MovieList;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;
    private static final String DATABASE_NAME = "movies_db";

    private final Handler mHandler = new Handler();
    private final Splash splash = new Splash();
    private MovieDatabase movieDatabase;

    Call<List<Movie>> call;
    public List<Movie> movies;
    private static Retrofit retrofit = null;
    public static final String URL = "https://api.androidhive.info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!dbExists(this,DATABASE_NAME))
        movieDatabase = Room.databaseBuilder(getApplicationContext(),MovieDatabase.class,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }

    private boolean dbExists(SplashActivity splashActivity, String databaseName) {
        File dbfile = splashActivity.getDatabasePath(databaseName);
        return dbfile.exists();
    }

    @Override
    protected void onStart() {
        super.onStart();
        movies = new ArrayList<>();
        ConnectivityManager connMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMan != null;
        NetworkInfo netInfo = connMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            if (!dbExists(this,DATABASE_NAME))
            connect();
        }
        mHandler.postDelayed(splash, SPLASH_DELAY);

    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(splash);
        super.onStop();
    }

    private class Splash implements Runnable {
        @Override
        public void run() {
            launchActivity();
        }
    }

    private void launchActivity() {
        if (!isFinishing()) {
            Intent intent = new Intent(this, MainActivity.class);
            MovieList movieList = new MovieList(movies);
            intent.putExtra("movies", movieList);
            startActivity(intent);
            overridePendingTransition(R.anim.in, R.anim.out);
            finish();
        }
    }

    private void connect() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder().baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        final MovieApi service = retrofit.create(MovieApi.class);
        call = service.getJson();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movies = response.body();
                save(movies);

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("FAIL", t.toString());
            }
        });
    }

    private void save(final List<Movie> m) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDatabase.daoAccess().insertMultipleMovies(m);
            }
        }).start();
    }
}
