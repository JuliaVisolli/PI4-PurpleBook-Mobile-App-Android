package com.example.littlewolf_pc.app.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.activity.ComentarioActivity;
import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiCurtida;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
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
public class ProfileFriendFragment extends Fragment {

    LinearLayout moldura;
    private Button btnAmigos;
    private Button btnFotos;
    private Button btnComentario;
    private Button btnCurtida;
    Integer idHistoria;
    Integer idUsuario;
    String nomeAmigo;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ProfileFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Dialog loading = new Dialog(getContext(), android.R.style.Theme_Black);
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading.setContentView(viewDialog);
        loading.setCancelable(false);
        loading.show();

        final View view = inflater.inflate(R.layout.fragment_profile_friend, container, false);
        moldura = view.findViewById(R.id.containerCards);

        moldura = view.findViewById(R.id.linear);
        btnAmigos = view.findViewById(R.id.btnAmigos);
        btnFotos = view.findViewById(R.id.btnFotos);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idUsuario = bundle.getInt("idAmigo", -1);
            nomeAmigo = bundle.getString("nomeAmigo");
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.main_frame, new ListaAmigosFragment());
                transaction.commit();

            }
        };
        btnAmigos.setOnClickListener(listener);


        if(idUsuario != null){

            ApiUsuario apiUsuario = retrofit.create(ApiUsuario.class);

            CircularImageView userImage = view.findViewById(R.id.user_img);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
            imageLoader.displayImage("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + idUsuario, userImage);
            TextView nomeUsuario = view.findViewById(R.id.nome);
            nomeUsuario.setText(nomeAmigo);

            Call<String> fotoQuantCall = apiUsuario.getCountAllFotoByUserID(String.valueOf(idUsuario));

            Callback<String> fotoQuantCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String quantFoto = response.body();

                    if (quantFoto != null && response.code() == 200) {
                        btnFotos.setText(quantFoto);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            };
            fotoQuantCall.enqueue(fotoQuantCallback);

            Call<String> amigoQuantCall = apiUsuario.getCountAllAmigosByUserID(String.valueOf(idUsuario));

            Callback<String> amigoQuantCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String quantAmigo = response.body();

                    if (quantAmigo != null && response.code() == 200) {
                        btnAmigos.setText(quantAmigo);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            };
            amigoQuantCall.enqueue(amigoQuantCallback);

            Call<List<UsuarioDTO>> usuarioDTOCall = apiUsuario.perfilUsuario(String.valueOf(idUsuario));

            Callback<List<UsuarioDTO>> hiListCallback = new Callback<List<UsuarioDTO>>() {
                @Override
                public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                    List<UsuarioDTO> perfilUsuario = response.body();

                    if(perfilUsuario != null && response.code() == 200){
                        loading.dismiss();

                        for (UsuarioDTO usuarioDTO: perfilUsuario) {

                            idHistoria = usuarioDTO.getHistoria().getId();
                            if(usuarioDTO.getHistoria() != null){

                                if (usuarioDTO.getHistoria().getFoto() == null) {

                                    addItemSFoto(usuarioDTO.getNome(), usuarioDTO.getHistoria().getData(), usuarioDTO.getHistoria().getTexto(),
                                            "http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + usuarioDTO.getId(), usuarioDTO.getHistoria().getTotalCurtidas().toString(), usuarioDTO.getHistoria().getTotalComentarios().toString(), usuarioDTO.getHistoria().getId());

                                } else {
                                    addItem("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + usuarioDTO.getId(), usuarioDTO.getNome(), usuarioDTO.getHistoria().getData(), usuarioDTO.getHistoria().getTexto(),
                                            "http://josiasveras.azurewebsites.net/WSEcommerce/rest/historia/image/" + usuarioDTO.getHistoria().getId(), usuarioDTO.getHistoria().getTotalCurtidas().toString(), usuarioDTO.getHistoria().getTotalComentarios().toString(), usuarioDTO.getHistoria().getId());

                                }
                            }

                        }

                    }

                }
                @Override
                public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                    t.printStackTrace();
                    loading.dismiss();


                }
            };
            usuarioDTOCall.enqueue(hiListCallback);
        }




        return view;
    }

    private void addItem(String url, String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL, String quantidadeCurtida, String quantidadeComentario, Integer idHistoria){
        final CardView cardView;


        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.card,
                        moldura, false);

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

        moldura.addView(cardView);

        carregarImagemPerfil(imageURL, cardView);
        carregarImagemHistoria(url, cardView);
    }

    private void addItemSFoto(/*String url,*/ String textoDoTitulo, Date textoDaHora, String textoDaMensagem, String imageURL, String quantidadeCurtida, String quantidadeComentario, Integer idHistoria){
        final CardView cardView;
        cardView = (CardView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.card,
                        moldura, false);
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

        moldura.addView(cardView);

        carregarImagemHistoria(imageURL, cardView);


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
