package com.example.littlewolf_pc.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
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
    private Button btnAddFriend;
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


        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_amigo);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_amigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtNome = mDialog.findViewById(R.id.dialog_name_id);
                TextView txtEmail = mDialog.findViewById(R.id.dialog_email_id);
                CircularImageView image = mDialog.findViewById(R.id.dialog_img);
                txtNome.setText(mData.get(vHolder.getAdapterPosition()).getNome());
                txtEmail.setText(mData.get(vHolder.getAdapterPosition()).getEmail());

//                if(mData.get(vHolder.getAdapterPosition()).getFoto() != null){
//                    byte [] encodeByte=Base64.decode(mData.get(vHolder.getAdapterPosition()).getFoto(), Base64.DEFAULT);
//                    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                    image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 180,
//                            180, false));
//
//                }
//
//                View.OnClickListener listener2 = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
//                        if(idUsuario != null) {
//                            ApiAmizade apiAmizade = retrofit.create(ApiAmizade.class);
//                            AmizadeDTO amizadeDTO = new AmizadeDTO();
//
//                            amizadeDTO.setUsuario1(new UsuarioDTO(idUsuario));
//                            amizadeDTO.setUsuario2(new UsuarioDTO(mData.get(vHolder.getAdapterPosition()).getId()));
//                            Call<AmizadeDTO> amizadeDTOCall = apiAmizade.solicitaAmizade(amizadeDTO);
//
//
//                            Callback<AmizadeDTO> amizadeDTOCallback = new Callback<AmizadeDTO>() {
//                                @Override
//                                public void onResponse(Call<AmizadeDTO> call, Response<AmizadeDTO> response) {
//                                    AmizadeDTO amizade = response.body();
//
//                                    if (amizade != null && response.code() == 200) {
//                                        Toast.makeText(mContext, "Adicionado com sucesso" + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                }
//
//
//                                @Override
//                                public void onFailure(Call<AmizadeDTO> call, Throwable t) {
//                                    t.printStackTrace();
//
//                                }
//                            };
//                            amizadeDTOCall.enqueue(amizadeDTOCallback);
//                        }
//
//                    }
//                };
//
//                btnAddFriend.setOnClickListener(listener2);

                Button btnSee = mDialog.findViewById(R.id.dialog_btn_see);
//                byte[] bytes = Base64.decode(mData.get(vHolder.getAdapterPosition()).getFoto(), Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                image.setImageBitmap(bitmap);
//                                Toast.makeText(mContext, "Test Click" + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((InternalActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ProfileFriendFragment()).commit();
                        mDialog.dismiss();
                    }
                };
                btnSee.setOnClickListener(listener);

                mDialog.show();
            }
        });
//        clickButtonAddAmigo();
        return vHolder;
    }

    public void clickButtonAddAmigo(){




    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_nome.setText(mData.get(position).getNome());
        holder.tv_email.setText(mData.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nome;
        private TextView tv_email;
        private CardView item_amigo;
        private CircularImageView circula_image_view;
        private Button dialog_btn_see;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_amigo = itemView.findViewById(R.id.item_amigo);
            tv_nome = itemView.findViewById(R.id.nome);
            tv_email = itemView.findViewById(R.id.email);
            circula_image_view = itemView.findViewById(R.id.imagem);
            dialog_btn_see = itemView.findViewById(R.id.dialog_btn_see);

        }
    }

    public void updateList(List<UsuarioDTO> newList){
        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}
