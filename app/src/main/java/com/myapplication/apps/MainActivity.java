package com.myapplication.apps;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textViewUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUserDetails = findViewById(R.id.user_details);

        String userDetails = getIntent().getStringExtra("USER_DETAILS");
        textViewUserDetails.setText(userDetails);
    }
}
