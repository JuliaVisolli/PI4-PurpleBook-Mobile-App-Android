package com.example.littlewolf_pc.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.model.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<UsuarioDTO> mData;
    Dialog mDialog;

    public RecyclerViewAdapter(Context mcontext, List<UsuarioDTO> mData){
        this.mContext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_amigo,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_amigo);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_amigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtNome = mDialog.findViewById(R.id.dialog_name_id);
                TextView txtEmail = mDialog.findViewById(R.id.dialog_email_id);
                ImageView image = mDialog.findViewById(R.id.dialog_img);
                txtNome.setText(mData.get(vHolder.getAdapterPosition()).getNome());
                txtEmail.setText(mData.get(vHolder.getAdapterPosition()).getEmail());
//        image.setImageResource(mData.get(vHolder.getAdapterPosition()).getFoto());
                Toast.makeText(mContext, "Test Click" + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                mDialog.show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_nome.setText(mData.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nome;
        private LinearLayout item_amigo;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_amigo = itemView.findViewById(R.id.item_amigo);
            tv_nome = itemView.findViewById(R.id.nome);
        }
    }

    public void updateList(List<UsuarioDTO> newList){
        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}
