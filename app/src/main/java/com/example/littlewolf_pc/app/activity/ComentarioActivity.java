package com.example.littlewolf_pc.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.littlewolf_pc.app.model.ComentarioDTO;

import java.util.ArrayList;
import java.util.List;

import com.example.littlewolf_pc.app.R;

public class ComentarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        List<ComentarioDTO> comentarioDTOList = new ArrayList<>();

        comentarioDTOList.add(new ComentarioDTO(0, "Olá", null));
        comentarioDTOList.add(new ComentarioDTO(0, "Olá", null));
        comentarioDTOList.add(new ComentarioDTO(0, "Olá", null));

        ListView lista = findViewById(R.id.lista);

        AdapterListerComentario AdapterListerComentario = new AdapterListerComentario(comentarioDTOList, this);

        lista.setAdapter(AdapterListerComentario);
    }
}

