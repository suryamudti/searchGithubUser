package com.smile.searchgithubuser.api;

import com.smile.searchgithubuser.model.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by suryamudti on 29/10/18.
 */

public interface UserApi {

    @GET("search/users")
    @Headers({
            "Content-Type: application/json"
    })
    Call<Users> getUsers(@Query("q") String q);
}
