package vyvital.mymovies.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import vyvital.mymovies.R;

public class MovieHolder extends RecyclerView.ViewHolder {

    ImageView image;

    public MovieHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.moviePic);
    }
}
