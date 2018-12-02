package com.example.littlewolf_pc.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.fragment.ListaAmigosFragment;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class AdapterListerAmigos extends BaseAdapter {
    private List<UsuarioDTO> listaAmigosDTOList;
    private Fragment listaAmigos;

    public AdapterListerAmigos(List<UsuarioDTO> listaAmigosDTOList, Fragment listaAmigos){
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

        TextView txtTitulo = view.findViewById(R.id.nome_amigo);
        TextView txtEmail = view.findViewById(R.id.email_amigo);
        CircularImageView imagemAmigo = view.findViewById(R.id.img_amigo);

        UsuarioDTO listaAmigosDTO = listaAmigosDTOList.get(position);

        txtTitulo.setText(listaAmigosDTO.getNome());
        txtEmail.setText(listaAmigosDTO.getEmail());

        Integer idUsuario = listaAmigosDTO.getId();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(inflater.getContext()));
        imageLoader.displayImage("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + idUsuario, imagemAmigo);

        return view;
    }
}
