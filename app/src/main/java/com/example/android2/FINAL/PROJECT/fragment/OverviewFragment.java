package com.example.android2.FINAL.PROJECT.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.SocketTimeoutException;

import com.example.android2.FINAL.PROJECT.DetailActivity;
import com.example.android2.FINAL.PROJECT.R;
import com.example.android2.FINAL.PROJECT.adapter.MovieAdapter;
import com.example.android2.FINAL.PROJECT.model.Movie;
import com.example.android2.FINAL.PROJECT.network.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewFragment extends Fragment implements MovieAdapter.OnMovieItemSelectedListener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter movieAdapter;

    private int pageNumber = 1;

    private DataSource api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, viewGroup, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mvList);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState == null) {

            movieAdapter = new MovieAdapter(getContext());
            movieAdapter.setOnMovieItemSelectedListener(this);

            gridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);

            recyclerView.setAdapter(movieAdapter);

            loadMovie();
        }
    }

    private void loadMovie(){

        api = new DataSource();
        api.getPopularMovies(pageNumber, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Movie movie = (Movie) response.body();

                if(movie != null) {
                    if(movieAdapter != null) {
                        movieAdapter.addAll(movie.getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {
        DetailActivity.start(getContext(), movieAdapter.getItem(position));
    }

}
