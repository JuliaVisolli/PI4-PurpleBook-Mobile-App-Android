package com.example.littlewolf_pc.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.activity.ComentarioActivity;
import com.example.littlewolf_pc.app.activity.InternalActivity;
import com.example.littlewolf_pc.app.fragment.ProfileFriendFragment;
import com.example.littlewolf_pc.app.model.AmizadeDTO;
import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiAmizade;
import com.example.littlewolf_pc.app.resource.ApiCurtida;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<UsuarioDTO> mData;
    Dialog mDialog;
    Integer idAmigo;
    String nomeAmigo;
    Integer id;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RecyclerViewAdapter(Context mcontext, List<UsuarioDTO> mData){
        this.mContext = mcontext;
        this.mData = mData;
        id = null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.grid_amigo
                ,parent,false);
       final MyViewHolder vHolder = new MyViewHolder(v);
        final Dialog loading = new Dialog(mContext, android.R.style.Theme_Black);
        View viewDialog = LayoutInflater.from(mContext).inflate(R.layout.loading_dialog, null);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading.setContentView(viewDialog);
        loading.setCancelable(false);


        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_amigo);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.grid_amigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtNome = mDialog.findViewById(R.id.dialog_name_id);
                TextView txtEmail = mDialog.findViewById(R.id.dialog_email_id);
                CircularImageView imagem = mDialog.findViewById(R.id.dialog_img);
                txtNome.setText(mData.get(vHolder.getAdapterPosition()).getNome());
                txtEmail.setText(mData.get(vHolder.getAdapterPosition()).getEmail());

                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

                id = mData.get(vHolder.getAdapterPosition()).getId();
                final String nome = mData.get(vHolder.getAdapterPosition()).getNome();

                imageLoader.displayImage("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + id, imagem);

                Button btnSee = mDialog.findViewById(R.id.dialog_btn_see);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Bundle bundle = new Bundle();
                        bundle.putInt("idAmigo", id);
                        bundle.putString("nomeAmigo", nome);
                        ProfileFriendFragment fragment = new ProfileFriendFragment();

                        fragment.setArguments(bundle);
                        ((InternalActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

                        mDialog.dismiss();
                    }
                };
                btnSee.setOnClickListener(listener);

                Button btnAdicionarAmigo = mDialog.findViewById(R.id.dialog_btn_add);
                View.OnClickListener listenerAddAmigo = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final SharedPreferences prefs = mContext.getSharedPreferences("usuario", MODE_PRIVATE);

                        Integer idSolicitante = prefs.getInt("id", 0);


                        loading.show();

                        if(idSolicitante > 0) {
                            ApiAmizade apiAmizade =
                                    retrofit.create(ApiAmizade.class);

                            AmizadeDTO amizade = new AmizadeDTO();
                            amizade.setUsuario1(new UsuarioDTO(idSolicitante));
                            amizade.setUsuario2(new UsuarioDTO(id));
                            amizade.setAprovada(true);
                            Call<AmizadeDTO> usuarioDTOCall = apiAmizade.solicitaAmizade(amizade);

                            Callback<AmizadeDTO> usuarioDTOCallback = new Callback<AmizadeDTO>() {
                                @Override
                                public void onResponse(Call<AmizadeDTO> call, Response<AmizadeDTO> response) {
                                    AmizadeDTO solicitacaoAMizade = response.body();
                                    mDialog.dismiss();


                                    if (solicitacaoAMizade == null || response.code() == 200) {
                                        loading.dismiss();
                                        Toast toast = Toast.makeText(mContext, "Amigo adicionado com sucesso :)", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<AmizadeDTO> call, Throwable t) {
                                    t.printStackTrace();
                                    loading.dismiss();

                                }
                            };
                            usuarioDTOCall.enqueue(usuarioDTOCallback);
                        }
                    }
                };
                btnAdicionarAmigo.setOnClickListener(listenerAddAmigo);



                mDialog.show();
            }
        });
        return vHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        idAmigo = mData.get(position).getId();
        nomeAmigo = mData.get(position).getNome();
        holder.tv_nome.setText(mData.get(position).getNome());
        holder.tv_email.setText(mData.get(position).getEmail());

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

        final Integer id = mData.get(position).getId();

        imageLoader.displayImage("http://josiasveras.azurewebsites.net/WSEcommerce/rest/usuario/image/" + id, holder.cv_imagem);
        mData.size();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nome;
        private TextView tv_email;
        private CardView grid_amigo;
        private CircularImageView cv_imagem;
        public MyViewHolder(View itemView) {
            super(itemView);
            grid_amigo = itemView.findViewById(R.id.grid_amigo);
            tv_nome = itemView.findViewById(R.id.nome);
            tv_email = itemView.findViewById(R.id.email);
            cv_imagem = itemView.findViewById(R.id.imagem);

        }
    }

    public void updateList(List<UsuarioDTO> newList){
        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}
