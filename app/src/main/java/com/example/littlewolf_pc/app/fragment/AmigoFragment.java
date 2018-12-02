package com.example.littlewolf_pc.app.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.RecyclerViewAdapter;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AmigoFragment extends Fragment implements SearchView.OnQueryTextListener{

    LinearLayout modura;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    List<UsuarioDTO>lstUsuarios = new ArrayList<UsuarioDTO>();
    RecyclerViewAdapter adapter;
    private View view;
    private List<UsuarioDTO> usuarioDTOList;
    private CardView cardView;


    public AmigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_amigo, container, false);

        recyclerView = view.findViewById(R.id.amigo_reclyclerview);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://josiasveras.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
        if(idUsuario != null) {
            ApiUsuario apiUsuario =
                    retrofit.create(ApiUsuario.class);
            Call<List<UsuarioDTO>> usuarioDTOCall = apiUsuario.selectAllUsuario();

            Callback<List<UsuarioDTO>> usuarioDTOCallback = new Callback<List<UsuarioDTO>>() {
                @Override
                public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                    usuarioDTOList = response.body();


                    if (usuarioDTOList != null && response.code() == 200) {
                        for (UsuarioDTO usuarioDTO : usuarioDTOList) {

                            if(usuarioDTO.getId() != idUsuario){
                                lstUsuarios.add(usuarioDTO);
                            }


                        }
                        adapter = new RecyclerViewAdapter(getActivity(), lstUsuarios);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(adapter);

                    }

                }

                @Override
                public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                    t.printStackTrace();

                }
            };
            usuarioDTOCall.enqueue(usuarioDTOCallback);
        }
        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.android_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }


}
