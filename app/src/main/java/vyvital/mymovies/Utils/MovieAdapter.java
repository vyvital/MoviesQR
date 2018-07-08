package vyvital.mymovies.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import vyvital.mymovies.R;
import vyvital.mymovies.fragments.MovieDetailFrag;
import vyvital.mymovies.fragments.MovieListFrag;
import vyvital.mymovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    public static final String TAG = MovieListFrag.class.getSimpleName();
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movieList = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, final int position) {
        final String image_url = movieList.get(position).getImage();
        Picasso.with(context).load(image_url).networkPolicy(NetworkPolicy.OFFLINE).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(context)
                        .load(image_url)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(holder.image);
            }
        });
        ViewCompat.setTransitionName(holder.image, movieList.get(position).getTitle());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("MOVIE", movieList.get(position));
                MovieDetailFrag frag = MovieDetailFrag.newInstance();
                frag.setArguments(bundle);
                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(holder.image, ViewCompat.getTransitionName(holder.image))
                        .addToBackStack(TAG)
                        .replace(R.id.content, frag)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
