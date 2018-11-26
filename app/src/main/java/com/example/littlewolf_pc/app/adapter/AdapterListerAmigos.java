package com.example.littlewolf_pc.app.adapter;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.model.ListAmigosDTO;

import java.util.List;

public class AdapterListerAmigos extends BaseAdapter {
    private List<ListAmigosDTO> listaAmigosDTOList;
    private Fragment listaAmigos;

    public AdapterListerAmigos(List<ListAmigosDTO> listaAmigosDTOList, Fragment listaAmigos){
        this.listaAmigosDTOList = listaAmigosDTOList;
        this.listaAmigos = listaAmigos;
    }


    @Override
    public int getCount() {

        return listaAmigosDTOList.size();
    }

    @Override
    public Object getItem(int position) {

        return listaAmigosDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return listaAmigosDTOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = listaAmigos.getLayoutInflater();

        View view = inflater.inflate(R.layout.item_amigo, parent, false);

        TextView txtTitulo = view.findViewById(R.id.titulo);
        TextView txtAmigos = view.findViewById(R.id.listaAmigos);
        ImageView notImagem = view.findViewById(R.id.imagem);

        ListAmigosDTO listaAmigosDTO = listaAmigosDTOList.get(position);

        txtTitulo.setText(listaAmigosDTO.getListAmigos());
        txtAmigos.setText(listaAmigosDTO.getListAmigos());
        notImagem.setImageResource(R.drawable.pb_logo);

        return view;
    }
}
