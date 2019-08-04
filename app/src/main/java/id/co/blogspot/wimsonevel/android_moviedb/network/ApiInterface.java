package id.co.blogspot.wimsonevel.android_moviedb.network;

import id.co.blogspot.wimsonevel.android_moviedb.model.Movie;
import id.co.blogspot.wimsonevel.android_moviedb.model.MovieDetail;
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
