package com.example.ruslan.usersservice.userlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ruslan.usersservice.R;
import com.example.ruslan.usersservice.UserDAO;
import com.example.ruslan.usersservice.UserDownloadService;
import com.example.ruslan.usersservice.api.ApiRequester;
import com.example.ruslan.usersservice.api.pojo.Users;
import com.example.ruslan.usersservice.userinfo.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

import static com.example.ruslan.usersservice.UserDownloadService.MY_ACTION;

public class UserListActivity extends AppCompatActivity implements OnUserItemClickListener, View.OnClickListener {

    private static final String USER_DATA = "user_pref";
    private static final String MY_LOG = "myLog";
    private RecyclerView recyclerView;
    private List<UserItem> users;
    private UserAdapter adapter;
    private ProgressBar progressBar;
    private Button btnClearData, btnRefresh;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(this);

        btnClearData = (Button) findViewById(R.id.btn_clear_data);
        btnClearData.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.pg_waiting);
        progressBar.setVisibility(View.VISIBLE);

        users = new ArrayList<>();
        adapter = new UserAdapter(users);
        adapter.setOnUserItemClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter(MY_ACTION));
        getUsersFromApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void getUsersFromApi() {
        Log.d(MY_LOG, "getUsersFromApi");
        Intent intent = new Intent(this, UserDownloadService.class);
        startService(intent);
    }

    @Override
    public void onClickListener(int position) {
        Intent intent = new Intent(UserListActivity.this, UserInfoActivity.class);

        intent.putExtra("name", users.get(position).getName());
        intent.putExtra("surename", users.get(position).getSureName());
        intent.putExtra("city", users.get(position).getCity());
        intent.putExtra("gender", users.get(position).getGender());
        intent.putExtra("telephone", users.get(position).getTelephoneNumber());
        intent.putExtra("picture", users.get(position).getPictureLink());

        startActivity(intent);
    }


    private void onBroadcastReceive() {

        progressBar.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = this.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        Map<String, String> preferencesAll = (Map<String, String>) preferences.getAll();
        for (Map.Entry<String, String> userEntry : preferencesAll.entrySet()) {
            try {
                JSONObject jsonObject = new JSONObject(userEntry.getValue());
                UserItem userItem = new UserItem(jsonObject.getString("name"),
                        jsonObject.getString("surename"),
                        jsonObject.getString("city"),
                        jsonObject.getString("gender"),
                        jsonObject.getString("telephone"),
                        jsonObject.getString("picture"));

                users.add(userItem);
            } catch (JSONException e) {
                Log.d(MY_LOG, "something weong with JSON");
                e.printStackTrace();
            }
            Log.d(MY_LOG, "broadcst received");
            adapter.notifyDataSetChanged();
        }

    }

    private void clearPreferences() {
        UserDAO userDAO = new UserDAO(this);
        userDAO.clearStorage();
        if (adapter != null) {
            adapter.deleteUsers();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_data:
                clearPreferences();

                break;
            case R.id.btn_refresh:
                clearPreferences();
                getUsersFromApi();
                break;
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            onBroadcastReceive();
        }
    }

}
