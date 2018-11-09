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
public class AmigoFragment extends Fragment {

    LinearLayout modura;

    public AmigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_card, container, false);
        modura = view.findViewById(R.id.containerCards);
        addItem("Nome do Amigo", "Alguma informação do amigo","https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg");
        return view;

    }

    private void addItem(String textoDoTitulo, String infoDoAmigo, String imageURL){
        CardView cardView;

        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.fragment_amigo,
                        modura, false);

        TextView titulo = cardView.findViewById(R.id.titulo);
        titulo.setText(textoDoTitulo);
        TextView infoamigo = cardView.findViewById(R.id.infoamigo);
        infoamigo.setText(infoDoAmigo);
       //TextView mensagem = cardView.findViewById(R.id.mensagem);
        //mensagem.setText(textoDaMensagem);


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
