package com.example.adrianadrbezerra1.pi4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView etEmail;
    private TextView etSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        };
        btnLogin.setOnClickListener(listener);
    }

}



