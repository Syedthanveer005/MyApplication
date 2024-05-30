package com.myapplication.apps;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextEmail, editTextMobile, editTextPassword;
    Button buttonReg;
    ProgressBar progressBar;
    TextView textView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextMobile = findViewById(R.id.mobileno);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String username = String.valueOf(editTextUsername.getText());
                String email = String.valueOf(editTextEmail.getText());
                String mobile = String.valueOf(editTextMobile.getText());
                String password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter the Username", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter the E-mail id", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(Register.this, "Enter the Mobile Number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter the password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when there's an error
                    return;
                }

                if (db.insertData(username, email, mobile, password)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                Cursor res = db.getData(email, password);
                if (res.getCount() == 0) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Email: " + res.getString(0) + "\n");
                    buffer.append("Username: " + res.getString(1) + "\n");
                    buffer.append("Mobile: " + res.getString(2) + "\n");
                    buffer.append("Password: " + res.getString(3) + "\n\n");
                }

                Intent intent = new Intent(Register.this, MainActivity.class);
                intent.putExtra("USER_DETAILS", buffer.toString());
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
