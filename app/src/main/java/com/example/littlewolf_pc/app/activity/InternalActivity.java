package com.example.littlewolf_pc.app.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.littlewolf_pc.app.fragment.AmigoFragment;
import com.example.littlewolf_pc.app.fragment.CardFragment;
import com.example.littlewolf_pc.app.fragment.LoginFragment;
import com.example.littlewolf_pc.app.fragment.NotificacaoFragment;
import com.example.littlewolf_pc.app.fragment.ProfileFragment;
import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.RecyclerViewAdapter;
import com.example.littlewolf_pc.app.model.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class InternalActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar toolbar;
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
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstUsuarios = new ArrayList<>();
        lstUsuarios.add(new UsuarioDTO(1, "Francini", null));
        lstUsuarios.add(new UsuarioDTO(1, "Julia", null));
        lstUsuarios.add(new UsuarioDTO(1, "Adriana", null));
        lstUsuarios.add(new UsuarioDTO(1, "Anna", null));
        lstUsuarios.add(new UsuarioDTO(1, "Jorzias", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio Abenza", null));
        lstUsuarios.add(new UsuarioDTO(1, "Ramon", null));
        lstUsuarios.add(new UsuarioDTO(1, "Anna", null));
        lstUsuarios.add(new UsuarioDTO(1, "Jorzias", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio Abenza", null));
        lstUsuarios.add(new UsuarioDTO(1, "Ramon", null));
        lstUsuarios.add(new UsuarioDTO(1, "Anna", null));
        lstUsuarios.add(new UsuarioDTO(1, "Jorzias", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio", null));
        lstUsuarios.add(new UsuarioDTO(1, "Fabio Abenza", null));
        lstUsuarios.add(new UsuarioDTO(1, "Ramon", null));

//
        recyclerView = findViewById(R.id.amigo_reclyclerview);
//        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, lstUsuarios);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new RecyclerViewAdapter(this, lstUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


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
                            Intent i = new Intent(InternalActivity.this, MainActivity.class);
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
                    setFragment(perfilFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.android_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                Toast.makeText(InternalActivity.this, "Action View Expand", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                Toast.makeText(InternalActivity.this, "Action View Collapsed", Toast.LENGTH_SHORT).show();
//                return true;
//
//            }
//        };

        MenuItem searchItem = menu.findItem(R.id.android_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<UsuarioDTO> newList = new ArrayList<>();

        for(UsuarioDTO usuarioDTO: lstUsuarios){
            if(usuarioDTO.getNome().toLowerCase().contains(userInput)){
                newList.add(usuarioDTO);
            }
        }
        adapter.updateList(newList);
        return true;
    }
}
