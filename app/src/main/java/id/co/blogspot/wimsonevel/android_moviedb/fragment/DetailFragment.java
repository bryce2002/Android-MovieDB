package id.co.blogspot.wimsonevel.android_moviedb.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.List;

import id.co.blogspot.wimsonevel.android_moviedb.R;
import id.co.blogspot.wimsonevel.android_moviedb.model.Genre;
import id.co.blogspot.wimsonevel.android_moviedb.model.MovieData;
import id.co.blogspot.wimsonevel.android_moviedb.model.MovieDetail;
import id.co.blogspot.wimsonevel.android_moviedb.network.ApiService;
import id.co.blogspot.wimsonevel.android_moviedb.network.Constant;
import id.co.blogspot.wimsonevel.android_moviedb.util.ConnectionUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wim on 5/29/17.
 */

public class DetailFragment extends Fragment {

    private Toolbar toolbar;
    private ImageView imgPoster;
    private TextView tvMovieTitle;
    private TextView tvMovieDate;
    private TextView tvMovieDuration;
    private TextView tvMovieGenre;
    private TextView tvMovieHomepage;
    private TextView tvMovieOverview;
    private LinearLayout viewTrailers;
    private LinearLayout viewReviews;

    private ProgressBar pgTrailers;
    private ProgressBar pgReviews;

    private MovieData movieData;

    private ApiService apiService;

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
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
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

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
            actionBar.setSubtitle(R.string.movie_detail);
        }

        movieData = getArguments().getParcelable(DetailFragment.class.getSimpleName());

        apiService = new ApiService();

        if(ConnectionUtil.isConnected(getContext())) {
            if(movieData != null) {
                loadMovieDetail(movieData.getId());
            }
        }else{
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void loadMovieDetail(int id) {
        apiService.getMovieDetail(id, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                MovieDetail movieDetail = (MovieDetail) response.body();

                if(movieDetail != null) {
                    Picasso.with(getContext())
                            .load(Constant.IMG_URL + movieDetail.getPosterPath())
                            .into(imgPoster);

                    tvMovieTitle.setText(movieDetail.getOriginalTitle());
                    tvMovieDate.setText(movieDetail.getReleaseDate());
                    tvMovieDuration.setText(movieDetail.getRuntime() + " Minutes");

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
