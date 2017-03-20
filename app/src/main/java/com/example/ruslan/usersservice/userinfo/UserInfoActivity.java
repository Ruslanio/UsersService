package com.example.ruslan.usersservice.userinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruslan.usersservice.R;
import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {
    private TextView tvName,tvSureName,tvCity,tvGender,tvTelephone;
    private ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);

        tvName = (TextView) findViewById(R.id.tv_name_info);
        tvSureName = (TextView) findViewById(R.id.tv_sure_name_info);
        tvCity = (TextView) findViewById(R.id.tv_city_info);
        tvGender = (TextView) findViewById(R.id.tv_gender_info);
        tvTelephone = (TextView) findViewById(R.id.tv_telephone_info);

        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("name"));
        tvSureName.setText(intent.getStringExtra("surename"));
        tvCity.setText(intent.getStringExtra("city"));
        tvGender.setText(intent.getStringExtra("gender"));
        tvTelephone.setText(intent.getStringExtra("telephone"));

        String url = intent.getStringExtra("picture");
        Picasso.with(this).load(url).into(ivAvatar);
    }
}
