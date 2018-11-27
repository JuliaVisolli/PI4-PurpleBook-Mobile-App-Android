package com.example.littlewolf_pc.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.adapter.AdapterListerAmigos;
import com.example.littlewolf_pc.app.model.ListAmigosDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAmigosFragment extends Fragment {


    public ListaAmigosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lista_amigos, container, false);
        List<ListAmigosDTO> listaAmigosDTO = new ArrayList<ListAmigosDTO>();
        ListView listAmigos = view.findViewById(R.id.listaAmigos);


        AdapterListerAmigos adapter = new AdapterListerAmigos(listaAmigosDTO, this);

        listAmigos.setAdapter(adapter);

        listaAmigosDTO.add(new ListAmigosDTO(1, "Olá"));

        return view;
    }

}
