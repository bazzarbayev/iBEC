package com.example.ibec_test;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines?country=us&category=business&apiKey=d14db04d12c348a6bac286ea1cd84620")
    Call<GetNewsResponse> getNews();

}
