package com.example.littlewolf_pc.app.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.activity.ComentarioActivity;
import com.example.littlewolf_pc.app.activity.HistoriaActivity;
import com.example.littlewolf_pc.app.activity.InternalActivity;
import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiCurtida;
import com.example.littlewolf_pc.app.resource.ApiHistoria;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;
import com.github.siyamed.shapeimageview.CircularImageView;
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
    private Button btnCurtida;
    private TextView txtView;
    FloatingActionButton fabGoToHistoria;
    Integer idHistoria;
    Integer idAmigo;
    String nomeAmigo;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_card, container, false);
        modura = view.findViewById(R.id.containerCards);
        txtView = view.findViewById(R.id.textAuthorSign);



        Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
        if(idUsuario != null) {
        ApiHistoria apiHistoria =
                retrofit.create(ApiHistoria.class);
        Call<List<HistoriaDTO>> historiaDTOCall = apiHistoria.selectAllHistorias(String.valueOf(idUsuario));

        Callback<List<HistoriaDTO>> hiListCallback = new Callback<List<HistoriaDTO>>() {
                @Override
                public void onResponse(Call<List<HistoriaDTO>> call, Response<List<HistoriaDTO>> response) {
                    List<HistoriaDTO> historiaDTOList = response.body();

                    if (historiaDTOList != null && response.code() == 200) {

                        if(historiaDTOList.size() > 0){

                            for (HistoriaDTO historiaDTO : historiaDTOList) {

                                idHistoria = historiaDTO.getId();
                                idAmigo = historiaDTO.getUsuario().getId();
                                nomeAmigo = historiaDTO.getUsuario().getNome();
                                if (historiaDTO.getFoto() == null) {
                                    addItemSFoto(historiaDTO.getUsuario().getNome(), historiaDTO.getData(), historiaDTO.getTexto(),
                                            "http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + historiaDTO.getUsuario().getId(), historiaDTO.getTotalCurtidas().toString(), historiaDTO.getTotalComentarios().toString(), historiaDTO.getId());

                                }else {
                                    addItem("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + historiaDTO.getUsuario().getId(), historiaDTO.getUsuario().getNome(), historiaDTO.getData(), historiaDTO.getTexto(),
                                            "http://josiasveras.azurewebsites.net/WSEcommerce/rest/historia/image/" + historiaDTO.getId(), historiaDTO.getTotalCurtidas().toString(), historiaDTO.getTotalComentarios().toString(), historiaDTO.getId());
                                }

                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<List<HistoriaDTO>> call, Throwable t) {
                    t.printStackTrace();

                }
            };
            historiaDTOCall.enqueue(hiListCallback);

        }

        return view;

    }


    private void addItem(String url, String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL, String quantidadeCurtida, String quantidadeComentario, Integer idHistoria){
        final CardView cardView;


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
        quantComentario.setText(quantidadeComentario + " comentarios");
        btnComentario = cardView.findViewById(R.id.btn_comentario);
        btnCurtida = cardView.findViewById(R.id.btn_curtida);
        TextView txtViewID = cardView.findViewById(R.id.id);
        txtViewID.setText(String.valueOf(idHistoria));
        final String idHistoriaCard = txtViewID.getText().toString();




        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ComentarioActivity.class);
                intent.putExtra("idHistoria",Integer.valueOf(idHistoriaCard));

                startActivity(intent);
            }
        };

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
                if(idUsuario != null) {
                    ApiCurtida apiCurtida = retrofit.create(ApiCurtida.class);
                    CurtidaDTO curtidaDTO = new CurtidaDTO();


                    curtidaDTO.setUsuario(new UsuarioDTO(idUsuario));
                    curtidaDTO.setHistoria(new HistoriaDTO(Integer.valueOf(idHistoriaCard)));
                    Call<CurtidaDTO> curtidaDTOCall = apiCurtida.saveCurtida(curtidaDTO);

                    Callback<CurtidaDTO> curtidaCallback = new Callback<CurtidaDTO>() {
                        @Override
                        public void onResponse(Call<CurtidaDTO> call, Response<CurtidaDTO> response) {
                            CurtidaDTO curtida = response.body();

                            if (curtida != null && response.code() == 200) {

                                TextView quantCurtida = cardView.findViewById(R.id.contcurtida);
                                quantCurtida.setText(quantCurtida.getText());
                            }

                        }

                        @Override
                        public void onFailure(Call<CurtidaDTO> call, Throwable t) {
                            t.printStackTrace();

                        }
                    };
                    curtidaDTOCall.enqueue(curtidaCallback);
                }

            }
        };

        btnCurtida.setOnClickListener(listener2);

        btnComentario.setOnClickListener(listener);

        modura.addView(cardView);

        carregarImagemPerfil(imageURL, cardView);
        carregarImagemHistoria(url, cardView);
    }

    private void addItemSFoto(/*String url,*/ String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL, String quantidadeCurtida, String quantidadeComentario, Integer idHistoria){
        final CardView cardView;
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
        quantComentario.setText(quantidadeComentario + " comentarios");
        btnComentario = cardView.findViewById(R.id.btn_comentario);
        btnCurtida = cardView.findViewById(R.id.btn_curtida);
        TextView txtViewID = cardView.findViewById(R.id.id);
        txtViewID.setText(String.valueOf(idHistoria));
        final String idHistoriaCard = txtViewID.getText().toString();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ComentarioActivity.class);
                intent.putExtra("idHistoria",Integer.valueOf(idHistoriaCard));

                startActivity(intent);
            }
        };

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
                if(idUsuario != null) {
                    ApiCurtida apiCurtida = retrofit.create(ApiCurtida.class);
                    CurtidaDTO curtidaDTO = new CurtidaDTO();


                    curtidaDTO.setUsuario(new UsuarioDTO(idUsuario));
                    curtidaDTO.setHistoria(new HistoriaDTO(Integer.valueOf(idHistoriaCard)));
                    Call<CurtidaDTO> curtidaDTOCall = apiCurtida.saveCurtida(curtidaDTO);

                    Callback<CurtidaDTO> curtidaCallback = new Callback<CurtidaDTO>() {
                        @Override
                        public void onResponse(Call<CurtidaDTO> call, Response<CurtidaDTO> response) {
                            CurtidaDTO curtida = response.body();

                            if (curtida != null && response.code() == 200) {

                                TextView quantCurtida = cardView.findViewById(R.id.contcurtida);
                                quantCurtida.setText(quantCurtida.getText());
                            }

                        }

                        @Override
                        public void onFailure(Call<CurtidaDTO> call, Throwable t) {
                            t.printStackTrace();

                        }
                    };
                    curtidaDTOCall.enqueue(curtidaCallback);
                }

            }
        };

        btnCurtida.setOnClickListener(listener2);

        btnComentario.setOnClickListener(listener);


        ImageView iv = cardView.findViewById(R.id.imagem);
        iv.setVisibility(View.GONE);

        modura.addView(cardView);

        carregarImagemHistoria(imageURL, cardView);


    }

    private void carregarImagemHistoria(String url, CardView cardView){
        ImageView imagem = cardView.findViewById(R.id.image);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagem);


        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idAmigo", idAmigo);
                bundle.putString("nomeAmigo", nomeAmigo);
                ProfileFriendFragment fragment = new ProfileFriendFragment();

                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
            }
        });

    }

    private void carregarImagemPerfil(String url, CardView cardView){
        ImageView imagemPerfil = cardView.findViewById(R.id.imagem);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        imageLoader.displayImage(url, imagemPerfil);

    }




}
