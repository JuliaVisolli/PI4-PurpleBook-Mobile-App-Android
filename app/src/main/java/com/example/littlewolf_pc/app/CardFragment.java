package com.example.littlewolf_pc.app;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    LinearLayout modura;

    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_card, container, false);
        modura = view.findViewById(R.id.containerCards);
        addItem("Nome do usuário", "Hora", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id dolor a odio semper egestas. Quisque blandit, dolor vitae convallis consectetur, mauris lacus aliquam quam, eu sodales massa leo quis justo. Proin risus purus, convallis vitae nisi at, consectetur semper velit. Nulla velit ante, tristique sed ipsum et, convallis lobortis ipsum. ","https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg");
        return view;

    }

    private void addItem(String textoDoTitulo, String textoDaHora, String textoDaMensagem, String imageURL){
        CardView cardView;

        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.card,
                        modura, false);

        TextView titulo = cardView.findViewById(R.id.titulo);
        titulo.setText(textoDoTitulo);
        TextView hora = cardView.findViewById(R.id.hora);
        hora.setText(textoDaHora);
        TextView mensagem = cardView.findViewById(R.id.mensagem);
        mensagem.setText(textoDaMensagem);


        modura.addView(cardView);

        carregarImagem(imageURL, cardView);
    }

    private void carregarImagem(String url, CardView cardView){
        ImageView imagem = cardView.findViewById(R.id.imagem);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagem);

    }




}
