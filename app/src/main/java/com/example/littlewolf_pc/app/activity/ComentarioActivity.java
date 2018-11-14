package com.example.littlewolf_pc.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.adapter.AdapterListerComentario;
import com.example.littlewolf_pc.app.model.ComentarioDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.littlewolf_pc.app.R;

public class ComentarioActivity extends AppCompatActivity {

    private Button btnComent;
    List<ComentarioDTO> comentarioDTOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);


        enviarMensagem();



    }

    public void enviarMensagem(){

        comentarioDTOList = new ArrayList<>();

        comentarioDTOList.add(new ComentarioDTO(0, "Nome", null));

        ListView lista = findViewById(R.id.lista_comentarios);

        AdapterListerComentario AdapterListerComentario = new AdapterListerComentario(comentarioDTOList, this);

        lista.setAdapter(AdapterListerComentario);

        ListView listView;
        listView = (ListView) LayoutInflater.from(this)
                .inflate(R.layout.model_comentario,
                        null, false);
        btnComent = findViewById(R.id.btnComent);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                comentarioDTOList.add(new ComentarioDTO(2, "flw", new Date()));

            }
        };
        btnComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date data  = new Date();
                comentarioDTOList.add(new ComentarioDTO(2, "flw", new Date()));
            }
        });
    }
}

