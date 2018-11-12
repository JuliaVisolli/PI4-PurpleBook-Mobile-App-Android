package com.example.littlewolf_pc.app.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.model.NotificacaoDTO;

import java.util.List;

public class AdapterListerNotificacao extends BaseAdapter {

    private List<NotificacaoDTO> notificacaoDTOList;
    private Fragment notificacao;

    public AdapterListerNotificacao (List<NotificacaoDTO> notificacaoDTOList, Fragment notificacao){
        this.notificacaoDTOList = notificacaoDTOList;
        this.notificacao = notificacao;
    }


    @Override
    public int getCount() {

        return notificacaoDTOList.size();
    }

    @Override
    public Object getItem(int position) {

        return notificacaoDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return notificacaoDTOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = notificacao.getLayoutInflater();

        View view = inflater.inflate(R.layout.list_item, parent, false);

       TextView txtTitulo = view.findViewById(R.id.titulo);
       TextView txtNotificacao = view.findViewById(R.id.notificacao);
       ImageView notImagem = view.findViewById(R.id.imagem);

        NotificacaoDTO notificacaoDTO = notificacaoDTOList.get(position);

        txtTitulo.setText(notificacaoDTO.getNotificacao());
        txtNotificacao.setText(notificacaoDTO.getNotificacao());
        notImagem.setImageResource(R.drawable.baseline_face_24);

        return view;
    }
}
