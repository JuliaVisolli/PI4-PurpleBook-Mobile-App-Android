package com.example.adrianadrbezerra1.pi4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment lf = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, lf).commit();


    }
}
