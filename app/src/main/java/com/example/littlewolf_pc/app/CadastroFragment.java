package com.example.littlewolf_pc.app;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment {
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfSenha;
    private Date dtNasc;
    private RadioButton radioFem;
    private RadioButton radioMasc;
    private Button btnRegistro;


    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_cadastro, container, false);

            etNome = v.findViewById(R.id.etNome);
            //etSobrenome = v.findViewById(R.id.etSobrenome);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            etConfSenha = v.findViewById(R.id.etConfSenha);
            radioFem = v.findViewById(R.id.radioFem);
            radioMasc = v.findViewById(R.id.radioMasc);
            btnRegistro = v.findViewById(R.id.btnRegistro);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(etNome.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etSenha.getText().toString().isEmpty()){
                    mensagem("Campo Obrigatório", "Atenção");
                    return;

                }
                if(!etSenha.getText().toString().equalsIgnoreCase(etConfSenha.getText().toString())){
                    mensagem("Senha incorreta", "Atenção");
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://josiasveras.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UsuarioDTO usuarioDTO = new UsuarioDTO();

                usuarioDTO.setNome(etNome.getText().toString());
                usuarioDTO.setEmail(etEmail.getText().toString());
                usuarioDTO.setSenha(etSenha.getText().toString());

                ApiUsuario apiUsuario =
                        retrofit.create(ApiUsuario.class);
                Call<UsuarioDTO> usuarioDTOCall = apiUsuario.saveUsuario(usuarioDTO);

                Callback<UsuarioDTO> usuarioDTOCallback = new Callback<UsuarioDTO>() {
                    @Override
                    public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                        UsuarioDTO user =  response.body();

                        if(user != null && response.code() == 200){
                        }

                    }
                    @Override
                    public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                        t.printStackTrace();
                    }
                };
                usuarioDTOCall.enqueue(usuarioDTOCallback);

            }
        };

        btnRegistro.setOnClickListener(listener);




        return v;
    }
    private void mensagem(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder (CadastroFragment.this.getActivity());
        //Configura o corpo da mensagem
        builder.setMessage(message);
        //Configura o título da mensagem
        builder.setTitle(title);
        //Impede que o botão seja cancelável (possa clicar
        //em voltar ou fora para fechar)
        builder.setCancelable(false);
        //Configura um botão de OK para fechamento (um
        //outro listener pode ser configurado no lugar do "null")
        builder.setPositiveButton("OK", null);
        //Cria efetivamente o diálogo
        AlertDialog dialog = builder.create();
        //Faz com que o diálogo apareça na tela
        dialog.show();

    }

}
