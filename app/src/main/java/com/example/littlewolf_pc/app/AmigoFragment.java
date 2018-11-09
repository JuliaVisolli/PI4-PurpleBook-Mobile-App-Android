package com.example.littlewolf_pc.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://josiasveras.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUsuario apiUsuario =
                retrofit.create(ApiUsuario.class);
        Call<List<UsuarioDTO>> usuarioDTOCall = apiUsuario.selectAllUsuario();

        Callback<List<UsuarioDTO>> usuarioDTOCallback = new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                System.out.print(response.body());
                List<UsuarioDTO> usuarioDTOList = response.body();

                if(usuarioDTOList != null && response.code() == 200){
                    for (UsuarioDTO usuarioDTO: usuarioDTOList) {
                        addItem(usuarioDTO.getNome(), usuarioDTO.getEmail(), "https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg");

                    }

                }

            }
            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                t.printStackTrace();

            }
        };
        usuarioDTOCall.enqueue(usuarioDTOCallback);
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
