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

import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiHistoria;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
                        addItem("Nome user", historiaDTO.getData(),  historiaDTO.getTexto(), "https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg");

                    }

                }

            }
            @Override
            public void onFailure(Call<List<HistoriaDTO>> call, Throwable t) {
                t.printStackTrace();

            }
        };
        historiaDTOCall.enqueue(hiListCallback);
//        addItem("Nome do usuário", "Hora", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ","https://st3.depositphotos.com/12985790/18246/i/450/depositphotos_182461084-stock-photo-anonymous.jpg");
        return view;

    }

    private void addItem(String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL){
        CardView cardView;

        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.card,
                        modura, false);

        TextView titulo = cardView.findViewById(R.id.titulo);
        titulo.setText(textoDoTitulo);
        TextView hora = cardView.findViewById(R.id.hora);
        hora.setText(textoDaHora.toString());
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
