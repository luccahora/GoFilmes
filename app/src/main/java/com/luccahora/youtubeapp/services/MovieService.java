package com.luccahora.youtubeapp.services;

import com.luccahora.youtubeapp.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {


    @GET("3/movie/popular?")
    Call<Resultado> recuperarVideos(@Query("api_key") String api_key, @Query("language") String language);

    @GET("https://api.themoviedb.org/3/search/movie?language=pt-br&sort_by=popularity&include_video=1&")
    Call<Resultado> searchMovies (@Query("api_key") String api_key, @Query("query") String inputUser);

}
