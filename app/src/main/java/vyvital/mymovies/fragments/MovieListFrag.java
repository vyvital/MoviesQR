package vyvital.mymovies.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vyvital.mymovies.MainActivity;
import vyvital.mymovies.R;
import vyvital.mymovies.Utils.MovieAdapter;
import vyvital.mymovies.model.Movie;


public class MovieListFrag extends Fragment {

    public static final String TAG = MovieListFrag.class.getSimpleName();
    RecyclerView movieRV;
    public MainActivity mainActivity;
    List<Movie> myMovieList;
    private FloatingActionButton fab;

    public MovieListFrag() {
        // Required empty public constructor
    }

    public static MovieListFrag newInstance() {
        return new MovieListFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        movieRV = rootView.findViewById(R.id.MovieRV);
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Scan();
            }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            movieRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        else movieRV.setLayoutManager(new GridLayoutManager(getContext(), 3));
        movieRV.setHasFixedSize(true);
        movieRV.setAdapter(null);
        mainActivity = (MainActivity) getActivity();
        movieRV.setAdapter(new MovieAdapter(((MainActivity) this.getActivity()).getMovies(), getActivity()));
        return rootView;

    }


}
