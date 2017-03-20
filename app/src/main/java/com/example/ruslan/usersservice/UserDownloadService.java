package com.example.ruslan.usersservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.ruslan.usersservice.api.ApiRequester;
import com.example.ruslan.usersservice.api.pojo.Users;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Ruslan on 19.03.2017.
 */

public class UserDownloadService extends IntentService {


//    private static final int CAPACITY = 20;
    private static final String MY_LOG = "myLog";
    private static final String SERVICE_NAME = "user_service";
    public static final String MY_ACTION = "com.example.ruslan.usersservice";


    public UserDownloadService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ApiRequester apiRequester = ApiRequester.getInstance();
        UserDAO userDAO = new UserDAO(getApplicationContext());
        try {
            Response<Users> userResponse = apiRequester.getRandomUser();
            userDAO.loadUsers(userResponse.body().getResults());
            Intent broadcast = new Intent(MY_ACTION);
            sendBroadcast(broadcast);

        } catch (IOException e) {
            Log.d(MY_LOG,"no internet");
            e.printStackTrace();
            stopSelf();
        } catch (JSONException e) {
            Log.d(MY_LOG,"json problem");
            stopSelf();
            e.printStackTrace();
        }

        Log.d(MY_LOG,"onHandleIntent");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MY_LOG,"onDestroy");
    }
}

