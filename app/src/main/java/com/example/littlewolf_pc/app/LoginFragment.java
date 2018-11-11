package com.example.littlewolf_pc.app;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ProgressBar;

import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;

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
public class LoginFragment extends Fragment {
    private EditText etEmail;
    private EditText etSenha;
    private Button btnLogin;
    private Button btnRegistro;
    UsuarioDTO getLoggedUser;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_refactor, container, false);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            btnLogin = v.findViewById(R.id.btnLogin);
            btnRegistro = v.findViewById(R.id.btnRegistro);

        final ProgressBar progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().isEmpty()){
                    Snackbar.make(getView(), getResources().getString(R.string.email_required), Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(etSenha.getText().toString().isEmpty()){
                    Snackbar.make(getView(), getResources().getString(R.string.password_required), Snackbar.LENGTH_SHORT)
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

                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://josiasveras.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UsuarioDTO usuarioDTO = new UsuarioDTO();

                usuarioDTO.setEmail(etEmail.getText().toString());
                usuarioDTO.setSenha(etSenha.getText().toString());


                ApiUsuario apiUsuario =
                        retrofit.create(ApiUsuario.class);
                Call<UsuarioDTO> usuarioDTOCall = apiUsuario.login(usuarioDTO);

                Callback<UsuarioDTO> usuarioDTOCallback = new Callback<UsuarioDTO>() {
                    @Override
                    public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                        getLoggedUser = response.body();

                        if(getLoggedUser != null && response.code() == 200){
                            progressBar.setVisibility(View.GONE);
                            etEmail.setText("");
                            etSenha.setText("");
                            Intent intent = new Intent(getActivity(), Main2Activity.class);
                            startActivity(intent);
                        }

                    }
                    @Override
                    public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        etEmail.setText("");
                        etSenha.setText("");
//                        Toast.makeText(getActivity(), "Usuario ou senha invalidos", Toast.LENGTH_LONG).show();
                        Snackbar.make(getView(), getResources().getString(R.string.user_password_invalid), Snackbar.LENGTH_SHORT)
                                .show();
                        t.printStackTrace();

                    }
                };
                usuarioDTOCall.enqueue(usuarioDTOCallback);

            }
        };


        View.OnClickListener listener2 = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //criando fragmento de cadastro
                CadastroFragment cf = new CadastroFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.moldura, cf).commit();
            }
       };

        btnLogin.setOnClickListener(listener);
        btnRegistro.setOnClickListener(listener2);


        return v;
    }

    private void mensagem(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder (LoginFragment.this.getActivity());
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
