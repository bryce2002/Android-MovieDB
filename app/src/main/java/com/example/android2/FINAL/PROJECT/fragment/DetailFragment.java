package com.example.android2.FINAL.PROJECT.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;

import com.example.android2.FINAL.PROJECT.R;
import com.example.android2.FINAL.PROJECT.model.MovieData;
import com.example.android2.FINAL.PROJECT.model.MovieDetail;
import com.example.android2.FINAL.PROJECT.network.DataSource;
import com.example.android2.FINAL.PROJECT.network.MovieInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {

    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieDuration;
    private TextView movieOverview;

    private MovieData movieData;

    private DataSource api;

    public static DetailFragment newInstance(MovieData movieData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFragment.class.getSimpleName(), movieData);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, viewGroup, false);

        movieImage = (ImageView) view.findViewById(R.id.mvImage);
        movieTitle = (TextView) view.findViewById(R.id.mvTitle);
        movieDate = (TextView) view.findViewById(R.id.mvDate);
        movieDuration = (TextView) view.findViewById(R.id.mvDuration);
        movieOverview = (TextView) view.findViewById(R.id.mvOverview);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieData = getArguments().getParcelable(DetailFragment.class.getSimpleName());

        api = new DataSource();
        loadMovieDetail(movieData.getId());
    }

    private void loadMovieDetail(int id) {
        api.getMovieDetail(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                MovieDetail movieDetail = (MovieDetail) response.body();

                if(movieDetail != null) {
                    Picasso.with(getContext())
                            .load(MovieInfo.IMG_URL + movieDetail.getPosterPath())
                            .into(movieImage);

                    movieDate.setText(movieDetail.getReleaseDate());
                    movieDuration.setText(movieDetail.getRuntime() + " Minutes");
                    movieTitle.setText(movieDetail.getTitle());
                    movieOverview.setText(movieDetail.getOverview());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
