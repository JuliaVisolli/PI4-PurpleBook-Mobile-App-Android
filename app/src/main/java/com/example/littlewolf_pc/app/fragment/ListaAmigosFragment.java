package com.example.littlewolf_pc.app.fragment;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.AdapterListerAmigos;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAmigosFragment extends Fragment {


    List<UsuarioDTO> listaAmigosDTO = new ArrayList<>();
    ListView listAmigos;
    AdapterListerAmigos adapter;
    private List<UsuarioDTO> usuarioDTOList;


    public ListaAmigosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lista_amigos, container, false);
        listAmigos = view.findViewById(R.id.listAmigos);

        final Dialog loading = new Dialog(getContext(), android.R.style.Theme_Black);
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading.setContentView(viewDialog);
        loading.setCancelable(false);
        loading.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://josiasveras.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SharedPreferences prefs = getActivity().getSharedPreferences("usuario", MODE_PRIVATE);

        final Integer idUsuario = prefs.getInt("id", 0);
        if(idUsuario != null) {
            ApiUsuario apiUsuario =
                    retrofit.create(ApiUsuario.class);
            Call<List<UsuarioDTO>> usuarioDTOCall = apiUsuario.buscaAmigo(String.valueOf(idUsuario));

            Callback<List<UsuarioDTO>> usuarioDTOCallback = new Callback<List<UsuarioDTO>>() {
                @Override
                public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                    usuarioDTOList = response.body();


                    if (usuarioDTOList != null && response.code() == 200) {
                        loading.dismiss();
                        for (UsuarioDTO usuarioDTO : usuarioDTOList) {

                            if(usuarioDTO.getId() != idUsuario){
                                listaAmigosDTO.add(usuarioDTO);
                            }


                        }
                        adapter = new AdapterListerAmigos(getActivity(), listaAmigosDTO,ListaAmigosFragment.this);

                        listAmigos.setAdapter(adapter);

                    }

                }

                @Override
                public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                    t.printStackTrace();
                    loading.dismiss();

                }
            };
            usuarioDTOCall.enqueue(usuarioDTOCallback);
        }

        return view;
    }

}
