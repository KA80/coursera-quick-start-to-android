package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public static String USER_KEY = "USER_KEY";


    private AppCompatImageView mPhoto;
    private TextView mLogin;
    private TextView mPassword;

    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mPhoto = findViewById(R.id.ivPhoto);
        mLogin = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.tvPassword);

        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.get(USER_KEY);
        mLogin.setText(user.getLogin());
        mPassword.setText(user.getPassword());

        mPhoto.setOnClickListener(mOnPhotoClickListener);
    }
}