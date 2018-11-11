package com.example.littlewolf_pc.app;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Button btnRegistro;
    private Button btnLogar;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_cadastro, container, false);

            etNome = v.findViewById(R.id.etNome);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            etConfSenha = v.findViewById(R.id.etConfSenha);
            btnRegistro = v.findViewById(R.id.btnRegistro);
            btnLogar = v.findViewById(R.id.btnLogar);

        final ProgressBar progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(etNome.getText().toString().isEmpty()){
                    Snackbar.make(getView(), getResources().getString(R.string.nome_required), Snackbar.LENGTH_SHORT)
                            .show();
                    return;

                }
                if(etEmail.getText().toString().isEmpty()){
                    Snackbar.make(getView(), getResources().getString(R.string.email_required), Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(etEmail.getText().toString() != null){
                    boolean emailValido = validate(etEmail.getText().toString());
                    if(!emailValido){
                        Snackbar.make(getView(), getResources().getString(R.string.valid_email), Snackbar.LENGTH_SHORT)
                                .show();
                        return;
                    }
                }


                if(etSenha.getText().toString().isEmpty()){
                    Snackbar.make(getView(), getResources().getString(R.string.password_required), Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(!etSenha.getText().toString().equalsIgnoreCase(etConfSenha.getText().toString())){
                    Snackbar.make(getView(), getResources().getString(R.string.confirm_password_required), Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

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
                            progressBar.setVisibility(View.GONE);
                            etNome.setText("");
                            etEmail.setText("");
                            etSenha.setText("");
                            etConfSenha.setText("");
                            Toast.makeText(getActivity(), getResources().getString(R.string.success), Toast.LENGTH_LONG).show();

                            LoginFragment lf = new LoginFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.moldura, lf).commit();
                        }

                    }
                    @Override
                    public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        etNome.setText("");
                        etEmail.setText("");
                        etSenha.setText("");
                        etConfSenha.setText("");
                        Snackbar.make(getView(), getResources().getString(R.string.error), Snackbar.LENGTH_SHORT)
                                .show();
                        t.printStackTrace();
                    }
                };
                usuarioDTOCall.enqueue(usuarioDTOCallback);

            }
        };

        btnRegistro.setOnClickListener(listener);

        View.OnClickListener listener2 = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LoginFragment lf = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.moldura, lf).commit();
            }
        };

        btnLogar.setOnClickListener(listener2);


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

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
