package com.example.littlewolf_pc.app.activity;

import android.app.Activity;
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
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiComentario;
import com.example.littlewolf_pc.app.resource.ApiHistoria;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComentarioActivity extends AppCompatActivity {

    private Button btnComent;
    List<ComentarioDTO> comentarioDTOList;
    List<ComentarioDTO>lstComentarios = new ArrayList<ComentarioDTO>();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        btnComent = findViewById(R.id.btnComent);
        ApiComentario apiComentario =
                retrofit.create(ApiComentario.class);
        Call<List<ComentarioDTO>> comentarioDTOCall = apiComentario.getAllComentariosByIdHistoria("1");

        Callback<List<ComentarioDTO>> comentarioCallBack = new Callback<List<ComentarioDTO>>() {
            @Override
            public void onResponse(Call<List<ComentarioDTO>> call, Response<List<ComentarioDTO>> response) {
                comentarioDTOList = response.body();

                if (comentarioDTOList != null && response.code() == 200) {

                    if(comentarioDTOList.size() > 0){

                        for (ComentarioDTO comentarioDTO : comentarioDTOList) {

                            lstComentarios.add(new ComentarioDTO(comentarioDTO.getId(), comentarioDTO.getTexto(), comentarioDTO.getData()));
                        }
                        AdapterListerComentario AdapterListerComentario = new AdapterListerComentario(lstComentarios, ComentarioActivity.this);
                        ListView lista = findViewById(R.id.lista_comentarios);
                        lista.setAdapter(AdapterListerComentario);

                    }

                }

            }

            @Override
            public void onFailure(Call<List<ComentarioDTO>> call, Throwable t) {
                t.printStackTrace();

            }
        };
        comentarioDTOCall.enqueue(comentarioCallBack);
        enviarMensagem();



    }

    public void enviarMensagem(){
        
        ListView listView;
        listView = (ListView) LayoutInflater.from(this)
                .inflate(R.layout.model_comentario,
                        null, false);


        btnComent.setOnClickListener(new View.OnClickListener() {
                ListView lista = findViewById(R.id.lista_comentarios);
                AdapterListerComentario AdapterListerComentario = new AdapterListerComentario(lstComentarios, ComentarioActivity.this);
            @Override
            public void onClick(View v) {
                lstComentarios.add(new ComentarioDTO(2, "flw", new Date()));

                lista.setAdapter(AdapterListerComentario);
            }
        });
    }
}

