package com.example.littlewolf_pc.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.littlewolf_pc.app.fragment.LoginFragment;
import com.example.littlewolf_pc.app.R;

public class MainActivity extends AppCompatActivity {

//    private NavigationView navigationView;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment lf = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.moldura, lf).commit();

    }









}