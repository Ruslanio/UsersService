package com.example.ruslan.usersservice.api;

import com.example.ruslan.usersservice.api.pojo.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ruslan on 19.03.2017.
 */

public interface ApiGetUser {
    @GET("/api")
    Call<Users> getUser(@Query("results") int count);
}
