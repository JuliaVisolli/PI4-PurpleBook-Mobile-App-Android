package com.example.littlewolf_pc.app.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.RecyclerViewAdapter;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
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
public class AmigoFragment extends Fragment  implements SearchView.OnQueryTextListener{

    LinearLayout modura;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private List<UsuarioDTO>lstUsuarios;
    RecyclerViewAdapter adapter;
    private View view;
    private List<UsuarioDTO> usuarioDTOList;

    public AmigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_amigo, container, false);
//        modura = view.findViewById(R.id.containerCards);

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

        recyclerView = view.findViewById(R.id.amigo_reclyclerview);
//
//        adapter = new RecyclerViewAdapter(getActivity(), lstUsuarios);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        recyclerView.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://josiasveras.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUsuario apiUsuario =
                retrofit.create(ApiUsuario.class);
        Call<List<UsuarioDTO>> usuarioDTOCall = apiUsuario.selectAllUsuario();

        Callback<List<UsuarioDTO>> usuarioDTOCallback = new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                System.out.print(response.body());
                usuarioDTOList = response.body();



                if(usuarioDTOList != null && response.code() == 200){
                    for (UsuarioDTO usuarioDTO: usuarioDTOList) {

                        adapter = new RecyclerViewAdapter(getActivity(), usuarioDTOList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(adapter);



                        if(usuarioDTO.getFoto() != null) {
//                            addItem(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getFoto().toString());

                        }
//                        addItem(usuarioDTO.getNome(), usuarioDTO.getEmail(), "https://cdn4.iconfinder.com/data/icons/web-app-flat-circular-icons-set/64/Iconos_Redondos_Flat_Usuario_Icn-512.png");

                    }

                }

            }
            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                t.printStackTrace();

            }
        };
        usuarioDTOCall.enqueue(usuarioDTOCallback);
        return view;

    }

//    private void addItem(String textoDoTitulo, String infoDoAmigo, String imageURL){
//        CardView cardView;
//
//        cardView = (CardView) LayoutInflater.from(this.getActivity())
//                .inflate(R.layout.fragment_amigo,
//                        modura, false);
//
//        TextView titulo = cardView.findViewById(R.id.titulo);
//        titulo.setText(textoDoTitulo);
////        TextView infoamigo = cardView.findViewById(R.id.infoamigo);
////        infoamigo.setText(infoDoAmigo);
//
//        modura.addView(cardView);
//
//        carregarImagem(imageURL, cardView);
//    }

    private void carregarImagem(String url, CardView cardView){
        ImageView imagem = cardView.findViewById(R.id.imagem);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagem);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
//        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<UsuarioDTO> newList = new ArrayList<>();

        for(UsuarioDTO usuarioDTO: usuarioDTOList){
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


//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
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
        super.onCreateOptionsMenu(menu,inflater);


//        return true;
    }


}
