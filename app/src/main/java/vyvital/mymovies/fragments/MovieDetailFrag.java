package vyvital.mymovies.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

import vyvital.mymovies.R;
import vyvital.mymovies.model.Movie;


public class MovieDetailFrag extends Fragment {

    private Movie movie;
    private TextView mTitle;
    private TextView mRelease;
    private TextView mRating;
    private ImageView mPoster;
    private Chip chip1;
    private Chip chip2;
    private Chip chip3;

    public MovieDetailFrag() {
        // Required empty public constructor
    }

    public static MovieDetailFrag newInstance() {
        return new MovieDetailFrag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if (getArguments() != null)
            movie = getArguments().getParcelable("MOVIE");
        if (movie != null) {
            initialize(view);
            Picasso.with(getActivity()).load(movie.getImage()).noFade().into(mPoster);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPoster.setTransitionName(movie.getTitle());
            }
            mTitle.setText(movie.getTitle());
            mRating.setText(movie.getRating() + "/10");
            mRelease.setText(String.valueOf(movie.getReleaseYear()));
            if (movie.getGenre() != null)
                if (movie.getGenre().size() == 1) {
                    chip1.setChipText(movie.getGenre().get(0));
                    chip1.setVisibility(View.VISIBLE);
                } else if (movie.getGenre().size() == 2) {
                    chip1.setChipText(movie.getGenre().get(0));
                    chip2.setChipText(movie.getGenre().get(1));
                    chip1.setVisibility(View.VISIBLE);
                    chip2.setVisibility(View.VISIBLE);
                } else if (movie.getGenre().size() == 3) {
                    chip1.setChipText(movie.getGenre().get(0));
                    chip2.setChipText(movie.getGenre().get(1));
                    chip3.setChipText(movie.getGenre().get(2));
                    chip1.setVisibility(View.VISIBLE);
                    chip2.setVisibility(View.VISIBLE);
                    chip3.setVisibility(View.VISIBLE);
                }


        }
        return view;
    }

    private void initialize(View view) {
        mPoster = view.findViewById(R.id.m_poster);
        mRelease = view.findViewById(R.id.m_release);
        mRating = view.findViewById(R.id.m_rating);
        mTitle = view.findViewById(R.id.m_title);
        chip1 = view.findViewById(R.id.chip1);
        chip2 = view.findViewById(R.id.chip2);
        chip3 = view.findViewById(R.id.chip3);
    }

}
