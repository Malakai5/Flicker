package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logInButton = findViewById(R.id.LogInButton);
        TextView title = findViewById(R.id.Title);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
    class LoginActivity extends Singleton{
        public LoginActivity() throws IOException {
        }
        TextInputEditText logInField = findViewById(R.id.LogInField);

        public void readUserName(){
            checkExistingProfile(Objects.requireNonNull(logInField.getText()).toString());



        }
    }

}
