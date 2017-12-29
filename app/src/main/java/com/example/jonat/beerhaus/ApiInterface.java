package com.example.jonat.beerhaus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jonat on 10/8/2017.
 */

public interface ApiInterface {

    @GET("{sort_by}")
    Call<BeerResponse> getBeerPages(@Path("sort_by") String mSortBy, @Query("page") int pageIndex, @Query("access_key") String apiKey);

}