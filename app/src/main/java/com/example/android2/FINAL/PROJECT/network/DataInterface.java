package com.example.android2.FINAL.PROJECT.network;

import com.example.android2.FINAL.PROJECT.model.Movie;
import com.example.android2.FINAL.PROJECT.model.MovieDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataInterface {

    @GET(MovieInfo.MOVIE_PATH + "/popular")
    Call<Movie> popularMovies(
            @Query("page") int page);

    @GET(MovieInfo.MOVIE_PATH + "/{movie_id}")
    Call<MovieDetail> movieDetail(
            @Path("movie_id") int movieId);

}
