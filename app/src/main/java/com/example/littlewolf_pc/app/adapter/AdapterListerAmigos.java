package com.example.littlewolf_pc.app.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.activity.InternalActivity;
import com.example.littlewolf_pc.app.fragment.ListaAmigosFragment;
import com.example.littlewolf_pc.app.fragment.ProfileFriendFragment;
import com.example.littlewolf_pc.app.model.AmizadeDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiAmizade;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class AdapterListerAmigos extends BaseAdapter {
    private List<UsuarioDTO> listaAmigosDTOList;
    private Fragment listaAmigos;
    Integer idAmigo;
    String nomeAmigo;
    Context mContext;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public AdapterListerAmigos(Context mcontext, List<UsuarioDTO> listaAmigosDTOList, Fragment listaAmigos){
        this.mContext = mcontext;
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

        final Dialog loading = new Dialog(mContext, android.R.style.Theme_Black);
        View viewDialog = LayoutInflater.from(mContext).inflate(R.layout.loading_dialog, null);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading.setContentView(viewDialog);
        loading.setCancelable(false);

        TextView txtTitulo = view.findViewById(R.id.nome_amigo);
        TextView txtEmail = view.findViewById(R.id.email_amigo);
        CircularImageView imagemAmigo = view.findViewById(R.id.img_amigo);
        Button btnRemove = view.findViewById(R.id.btn_remove_amigo);

        UsuarioDTO listaAmigosDTO = listaAmigosDTOList.get(position);

        txtTitulo.setText(listaAmigosDTO.getNome());
        txtEmail.setText(listaAmigosDTO.getEmail());

        Integer idUsuario = listaAmigosDTO.getId();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(inflater.getContext()));
        imageLoader.displayImage("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + idUsuario, imagemAmigo);

        idAmigo = listaAmigosDTO.getId();
        nomeAmigo = listaAmigosDTO.getNome();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idAmigo", idAmigo);
                bundle.putString("nomeAmigo", nomeAmigo);
                ProfileFriendFragment fragment = new ProfileFriendFragment();

                fragment.setArguments(bundle);
                ((InternalActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

            }});

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences prefs = mContext.getSharedPreferences("usuario", MODE_PRIVATE);

                Integer idSolicitante = prefs.getInt("id", 0);
                loading.show();

                if(idSolicitante != null) {
                    ApiAmizade apiAmizade =
                            retrofit.create(ApiAmizade.class);

                    Call<Integer> usuarioDTOCall = apiAmizade.recusarAmizade(idSolicitante, idAmigo);

                    Callback<Integer> usuarioDTOCallback = new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Integer solicitacaoAMizade = response.body();


                            if (solicitacaoAMizade != null && response.code() == 200) {
                                loading.dismiss();
                                Toast toast = Toast.makeText(mContext, "Amigo removido com sucesso :)", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            t.printStackTrace();
                            loading.dismiss();

                        }
                    };
                    usuarioDTOCall.enqueue(usuarioDTOCallback);
                }
            }
        });

        return view;
    }
}
