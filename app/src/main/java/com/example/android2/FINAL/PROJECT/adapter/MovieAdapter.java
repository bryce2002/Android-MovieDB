package com.example.android2.FINAL.PROJECT.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.android2.FINAL.PROJECT.R;
import com.example.android2.FINAL.PROJECT.model.MovieData;
import com.example.android2.FINAL.PROJECT.network.MovieInfo;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieData> movies;
    private Context context;

    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    private void add(MovieData item) {
        movies.add(item);
        notifyItemInserted(movies.size() - 1);
    }

    public void addAll(List<MovieData> movieDatas) {
        for (MovieData movieData : movieDatas) {
            add(movieData);
        }
    }

    public MovieData getItem(int position) {
        return movies.get(position);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = movieViewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (onMovieItemSelectedListener != null) {
                        onMovieItemSelectedListener.onItemClick(movieViewHolder.itemView, adapterPosition);
                    }
                }
            }
        });

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieData movieData = movies.get(position);
        holder.bind(movieData);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImg;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieImg = (ImageView) itemView.findViewById(R.id.img_thumb);
        }

        public void bind(MovieData movieData) {
            Picasso.with(context)
                    .load(MovieInfo.IMG_URL + movieData.getPosterPath())
                    .into(movieImg);
        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}
