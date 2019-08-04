package com.example.android2.FINAL.PROJECT.network;

import com.example.android2.FINAL.PROJECT.model.Movie;
import com.example.android2.FINAL.PROJECT.model.MovieDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Wim on 6/2/17.
 */

public interface ApiInterface {

    @GET(Constant.MOVIE_PATH + "/popular")
    Call<Movie> popularMovies(
            @Query("page") int page);

    @GET(Constant.MOVIE_PATH + "/{movie_id}")
    Call<MovieDetail> movieDetail(
            @Path("movie_id") int movieId);

}
