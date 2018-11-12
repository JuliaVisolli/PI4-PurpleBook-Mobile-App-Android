package com.example.littlewolf_pc.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.adapter.AdapterListerComentario;
import com.example.littlewolf_pc.app.model.ComentarioDTO;

import java.util.ArrayList;
import java.util.List;

import com.example.littlewolf_pc.app.R;

public class ComentarioActivity extends AppCompatActivity {

    private Button btnComent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        List<ComentarioDTO> comentarioDTOList = new ArrayList<>();

            comentarioDTOList.add(new ComentarioDTO(0, "Nome", null));

        ListView lista = findViewById(R.id.lista_comentarios);

        AdapterListerComentario AdapterListerComentario = new AdapterListerComentario(comentarioDTOList, this);

        lista.setAdapter(AdapterListerComentario);

        btnComent = findViewById(R.id.btnComent);

        btnComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}

