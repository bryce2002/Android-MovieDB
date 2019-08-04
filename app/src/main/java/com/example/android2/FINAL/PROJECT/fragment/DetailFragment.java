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

    private ImageView imgPoster;
    private TextView tvMovieTitle;
    private TextView tvMovieDate;
    private TextView tvMovieDuration;
    private TextView tvMovieOverview;

    private MovieData movieData;

    private DataSource apiService;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        imgPoster = (ImageView) view.findViewById(R.id.img_poster);
        tvMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        tvMovieDate = (TextView) view.findViewById(R.id.movie_date);
        tvMovieDuration = (TextView) view.findViewById(R.id.movie_duration);
        tvMovieOverview = (TextView) view.findViewById(R.id.movie_overview);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieData = getArguments().getParcelable(DetailFragment.class.getSimpleName());

        apiService = new DataSource();
        loadMovieDetail(movieData.getId());
    }

    private void loadMovieDetail(int id) {
        apiService.getMovieDetail(id, new Callback() {
            
            @Override
            public void onResponse(Call call, Response response) {
                MovieDetail movieDetail = (MovieDetail) response.body();

                if(movieDetail != null) {
                    Picasso.with(getContext())
                            .load(MovieInfo.IMG_URL + movieDetail.getPosterPath())
                            .into(imgPoster);

                    tvMovieDate.setText(movieDetail.getReleaseDate());
                    tvMovieDuration.setText(movieDetail.getRuntime() + " Minutes");
                    tvMovieTitle.setText(movieDetail.getTitle());
                    tvMovieOverview.setText(movieDetail.getOverview());
                }else{
                    Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if(t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
