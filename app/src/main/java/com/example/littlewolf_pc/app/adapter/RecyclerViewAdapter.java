package com.example.littlewolf_pc.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<UsuarioDTO> mData;
    Dialog mDialog;
    Integer idAmigo;
    String nomeAmigo;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RecyclerViewAdapter(Context mcontext, List<UsuarioDTO> mData){
        this.mContext = mcontext;
        this.mData = mData;
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
                CircularImageView image = mDialog.findViewById(R.id.dialog_img);
                txtNome.setText(mData.get(vHolder.getAdapterPosition()).getNome());
                txtEmail.setText(mData.get(vHolder.getAdapterPosition()).getEmail());

                Button btnSee = mDialog.findViewById(R.id.dialog_btn_see);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Bundle bundle = new Bundle();
                        bundle.putInt("idAmigo", idAmigo);
                        bundle.putString("nomeAmigo", nomeAmigo);
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
                        Integer idSolicitante = UsuarioSingleton.getInstance().getUsuario().getId();
                        loading.show();

                        if(idSolicitante != null) {
                            ApiAmizade apiAmizade =
                                    retrofit.create(ApiAmizade.class);

                            AmizadeDTO amizade = new AmizadeDTO();
                            amizade.setUsuario1(new UsuarioDTO(idSolicitante));
                            amizade.setUsuario2(new UsuarioDTO(idAmigo));
                            amizade.setAprovada(true);
                            Call<AmizadeDTO> usuarioDTOCall = apiAmizade.solicitaAmizade(amizade);

                            Callback<AmizadeDTO> usuarioDTOCallback = new Callback<AmizadeDTO>() {
                                @Override
                                public void onResponse(Call<AmizadeDTO> call, Response<AmizadeDTO> response) {
                                    AmizadeDTO solicitacaoAMizade = response.body();
                                    mDialog.dismiss();


                                    if (solicitacaoAMizade != null && response.code() == 200) {
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
        public MyViewHolder(View itemView) {
            super(itemView);
            grid_amigo = itemView.findViewById(R.id.grid_amigo);
            tv_nome = itemView.findViewById(R.id.nome);
            tv_email = itemView.findViewById(R.id.email);

        }
    }

    public void updateList(List<UsuarioDTO> newList){
        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}
