package com.example.littlewolf_pc.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.model.AdapterListerNotificacao;
import com.example.littlewolf_pc.app.model.NotificacaoDTO;
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
public class NotificacaoFragment extends Fragment {


    LinearLayout modura;
    public NotificacaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notificacao, container, false);
        List<NotificacaoDTO> notificacaoDTO = new ArrayList<NotificacaoDTO>();
        ListView lista = view.findViewById(R.id.lista);


        AdapterListerNotificacao adapter = new AdapterListerNotificacao(notificacaoDTO, this);

        lista.setAdapter(adapter);

        notificacaoDTO.add(new NotificacaoDTO(1, "Olá", null));

        return view;

    }


}
