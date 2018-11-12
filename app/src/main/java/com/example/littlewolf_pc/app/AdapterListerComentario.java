package com.example.littlewolf_pc.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littlewolf_pc.app.model.ComentarioDTO;

import java.util.List;

public class AdapterListerComentario extends BaseAdapter {

    private List<ComentarioDTO> comentarioDTOList;
    private Activity comentario;


    public AdapterListerComentario(List<ComentarioDTO> comentarioDTOList, Activity comentario){
        this.comentarioDTOList = comentarioDTOList;
        this.comentario = comentario;

    }

    @Override
    public int getCount() {

        return comentarioDTOList.size();
    }

    @Override
    public Object getItem(int position) {

        return comentarioDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return comentarioDTOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = comentario.getLayoutInflater();

        View view = inflater.inflate(R.layout.model_comentario, parent, false);

        TextView txtTitulo = view.findViewById(R.id.titulo);
        TextView txtComentario = view.findViewById(R.id.comentario);
        ImageView Imagem = view.findViewById(R.id.imagem);

        ComentarioDTO comentarioDTO = comentarioDTOList.get(position);

        txtTitulo.setText(comentarioDTO.getComentario());
        txtComentario.setText(comentarioDTO.getComentario());
        Imagem.setImageResource(R.drawable.baseline_face_24);

        return view;
    }
}
