package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
Singleton singleton = Singleton.getInstance();

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logInButton = findViewById(R.id.LogInButton);
        TextView title = findViewById(R.id.Title);
    }
    public void login (View view){
        TextInputEditText logInField = findViewById(R.id.LogInField);

       boolean userFound = singleton.checkExistingProfile(logInField.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (!userFound){
            builder.setTitle("Invalid User");
            builder.setMessage("Would you like to create a new profile?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Please put it username", Toast.LENGTH_SHORT);
                }
            });
        }
        AlertDialog logInResult = builder.create();
        logInResult.show();
    }



    }

