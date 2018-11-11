package com.example.littlewolf_pc.app;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.resource.ApiHistoria;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    LinearLayout modura;
    private Button btnComentario;

    public CardFragment() {
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

        ApiHistoria apiHistoria =
                retrofit.create(ApiHistoria.class);
        Call<List<HistoriaDTO>> historiaDTOCall = apiHistoria.selectAllHistorias();

        Callback<List<HistoriaDTO>> hiListCallback = new Callback<List<HistoriaDTO>>() {
            @Override
            public void onResponse(Call<List<HistoriaDTO>> call, Response<List<HistoriaDTO>> response) {
                System.out.print(response.body());
                List<HistoriaDTO> historiaDTOList = response.body();

                if(historiaDTOList != null && response.code() == 200){
                    for (HistoriaDTO historiaDTO: historiaDTOList) {

                        if(historiaDTO.getFoto() != null){
                            addItem(historiaDTO.getFoto().toString(), historiaDTO.getUsuario().getNome(), historiaDTO.getData(),  historiaDTO.getTexto(),
                             historiaDTO.getUsuario().getFoto().toString(), historiaDTO.getTotalCurtidas().toString(), historiaDTO.getTotalComentarios().toString());
                        }
                        addItem("https://cdn4.iconfinder.com/data/icons/web-app-flat-circular-icons-set/64/Iconos_Redondos_Flat_Usuario_Icn-512.png", historiaDTO.getUsuario().getNome(), historiaDTO.getData(),  historiaDTO.getTexto(),
                                "https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg", historiaDTO.getTotalCurtidas().toString(), historiaDTO.getTotalComentarios().toString());

                    }

                }

            }
            @Override
            public void onFailure(Call<List<HistoriaDTO>> call, Throwable t) {
                t.printStackTrace();

            }
        };
        historiaDTOCall.enqueue(hiListCallback);

        btnComentario = view.findViewById(R.id.btn_comentario);

//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ComentarioActivity.class);
//                startActivity(intent);
//            }
//        };
//
//       btnComentario.setOnClickListener(listener);


        return view;

    }

    private void addItem(String url, String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL, String quantidadeCurtida, String quantidadeComentario){
        CardView cardView;

        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.card,
                        modura, false);

        TextView titulo = cardView.findViewById(R.id.titulo);
        titulo.setText(textoDoTitulo);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format(textoDaHora);
        TextView hora = cardView.findViewById(R.id.hora);
        hora.setText(dateString);
        TextView mensagem = cardView.findViewById(R.id.mensagem);
        mensagem.setText(textoDaMensagem);
        TextView quantCurtida = cardView.findViewById(R.id.contcurtida);
        quantCurtida.setText(quantidadeCurtida);
        TextView quantComentario = cardView.findViewById(R.id.contcomentario);
        quantComentario.setText(quantidadeComentario);


        modura.addView(cardView);

        carregarImagemPerfil(imageURL, cardView);
        carregarImagemHistoria(url, cardView);
    }

    private void carregarImagemHistoria(String url, CardView cardView){
        ImageView imagem = cardView.findViewById(R.id.image);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagem);

    }

    private void carregarImagemPerfil(String url, CardView cardView){
        ImageView imagemPerfil = cardView.findViewById(R.id.imagem);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagemPerfil);

    }




}
