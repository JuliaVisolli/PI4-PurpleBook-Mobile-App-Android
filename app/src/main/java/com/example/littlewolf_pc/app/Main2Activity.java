package com.example.littlewolf_pc.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.littlewolf_pc.app.model.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ProfileFragment perfilFragment;
    private CardFragment feedFragment;
    private AmigoFragment amigoFragment;
    private NotificacaoFragment notificacaoFragment;
    private RecyclerView recyclerView;
    private List<UsuarioDTO> lstUsuarios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        lstUsuarios = new ArrayList<>();
        lstUsuarios.add(new UsuarioDTO(1, "Francini", null));
        lstUsuarios.add(new UsuarioDTO(1, "Julia", null));
        lstUsuarios.add(new UsuarioDTO(1, "Adriana", null));
        lstUsuarios.add(new UsuarioDTO(1, "Anna", null));
        lstUsuarios.add(new UsuarioDTO(1, "Jorzias", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio Abenza", null));
        lstUsuarios.add(new UsuarioDTO(1, "Ramon", null));


        recyclerView = findViewById(R.id.amigo_reclyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, lstUsuarios);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recyclerViewAdapter);

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
                        if (menuItem.getItemId() == R.id.main_frame) {
                            LoginFragment fragment = new LoginFragment();
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.main_frame, fragment).commit();
                            return true;
                        }
                        if (menuItem.getItemId() == R.id.action_friends) {
                            AmigoFragment amigoFragment = new AmigoFragment();
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.main_frame, amigoFragment).commit();
                            return true;
                        }
                        if (menuItem.getItemId() == R.id.action_logout) {
                            Intent i = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(i);
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

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        feedFragment = new CardFragment();
        amigoFragment = new AmigoFragment();
        notificacaoFragment = new NotificacaoFragment();
        perfilFragment = new ProfileFragment();

        setFragment(feedFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    setFragment(feedFragment);
                    return true;
                case R.id.navigation_perfil:
                    setFragment(feedFragment);
                    return true;
                case R.id.navigation_friend:
                    setFragment(amigoFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(notificacaoFragment);
                    return true;
            }
            return false;
            }
        });



    }

    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
