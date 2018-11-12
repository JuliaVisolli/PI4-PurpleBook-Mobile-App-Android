package com.example.littlewolf_pc.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.AdapterListerNotificacao;
import com.example.littlewolf_pc.app.model.NotificacaoDTO;

import java.util.ArrayList;
import java.util.List;


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
