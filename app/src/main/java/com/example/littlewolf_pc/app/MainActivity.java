package com.example.littlewolf_pc.app;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //LoginFragment lf = new LoginFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.moldura, lf).commit();

        CardFragment lf = new CardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.moldura, lf).commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.isChecked()) {
                            menuItem.setChecked(false);
                        } else {
                            menuItem.setChecked(true);
                        }
                        drawerLayout.closeDrawers();
                        if (menuItem.getItemId() == R.id.moldura) {
                            LoginFragment fragment = new LoginFragment();
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.moldura, fragment).commit();
                            return true;
                        }
                        return false;
                    }
                });

        drawerLayout = findViewById(R.id.drawer);
        //abrir fechar menu
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer) {
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







}