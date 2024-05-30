package com.myapplication.apps;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    ProgressBar progressBar;
    TextView textView;
    DatabaseHelper db;
    ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);
       myImageView = findViewById(R.id.user_logo);
        myImageView.setImageResource(R.drawable.logo);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Enter the E-mail id", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }

                Cursor res = db.getData(email, password);
                if (res.getCount() == 0) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Email: " + res.getString(0) + "\n");
                    buffer.append("Username: " + res.getString(1) + "\n");
                    buffer.append("Mobile: " + res.getString(2) + "\n");
                    buffer.append("Password: " + res.getString(3) + "\n\n");
                }

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("USER_DETAILS", buffer.toString());
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
