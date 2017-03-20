package com.example.ruslan.usersservice;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ruslan.usersservice.api.pojo.Result;
import com.example.ruslan.usersservice.userlist.UserItem;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ruslan on 19.03.2017.
 */

public class UserDAO {
    private static final String USER_DATA = "user_pref";

    private Context context;

    public UserDAO(Context context) {
        this.context = context;
    }


    public void loadUsers(List<Result> results) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Result result: results){
            String userID = result.getId().getValue();
            UserItem user = new UserItem(result);
            editor.putString(userID, user.toJSON());
        }
        editor.commit();
    }
    public void clearStorage(){
        SharedPreferences preferences = context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
