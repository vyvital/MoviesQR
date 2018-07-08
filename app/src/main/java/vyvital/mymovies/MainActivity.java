package vyvital.mymovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vyvital.mymovies.fragments.MovieListFrag;
import vyvital.mymovies.model.Movie;
import vyvital.mymovies.model.MovieDatabase;
import vyvital.mymovies.model.MovieList;

public class MainActivity extends AppCompatActivity {
    List<Movie> movies;
    private IntentIntegrator qrScan;
    public static final String TAG = MovieListFrag.class.getSimpleName();
    private MovieDatabase movieDatabase;
    private static final String DATABASE_NAME = "movies_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieDatabase = Room.databaseBuilder(getApplicationContext(),MovieDatabase.class,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        qrScan = new IntentIntegrator(this);
    //    MovieList movieList = getIntent().getExtras().getParcelable("movies");
        new Thread(new Runnable() {
            @Override
            public void run() {
                movies = movieDatabase.daoAccess().fetchMovies();
            }
        }).start();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, MovieListFrag.newInstance(), "frag")
                    .commit();
        } else {
            getSupportFragmentManager().findFragmentByTag("frag");
        }
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public void Scan() {
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    final Movie movie = new Movie();
                    JSONArray jsonArray = obj.getJSONArray("genre");
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++)
                        list.add(jsonArray.get(i).toString());
                    movie.setTitle(obj.getString("title"));
                    movie.setImage(obj.getString("image"));
                    movie.setRating(Float.valueOf(obj.getString("rating")));
                    movie.setReleaseYear(Integer.valueOf(obj.getString("releaseYear")));
                    movie.setGenre(list);
                    movies.add(movie);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            movieDatabase.daoAccess().insertOnlySingleMovie(movie);
                        }
                    }).start();

                    Toast.makeText(this, movie.getTitle() + " Was added to your Movie List", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
