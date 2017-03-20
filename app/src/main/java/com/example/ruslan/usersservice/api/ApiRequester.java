package com.example.ruslan.usersservice.api;

import com.example.ruslan.usersservice.api.pojo.Users;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ruslan on 19.03.2017.
 */

public class ApiRequester {
    private final String URL = "https://randomuser.me";
    private final int USERS_AMOUNT = 20;

    private static ApiRequester apiRequester;
    private ApiGetUser apiGetUser;

    private ApiRequester() {
    }

    public static ApiRequester getInstance() {
        if (apiRequester == null){
            apiRequester = new ApiRequester();
            apiRequester.init();
        }
        return apiRequester;
    }

    private  void init(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiGetUser = retrofit.create(ApiGetUser.class);
    }

    public Response<Users> getRandomUser() throws IOException {
        Call<Users> userCall = apiGetUser.getUser(USERS_AMOUNT);
        Response<Users> userResponse = userCall.execute();
        return userResponse;
    }
}
