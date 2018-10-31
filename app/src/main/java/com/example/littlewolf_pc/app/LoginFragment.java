package com.example.littlewolf_pc.app;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText etEmail;
    private EditText etSenha;
    private Button btnLogin;
    private Button btnRegistro;


    private static Pattern emailValidator = Pattern.compile("(?:[a-zA-Z0-9!#$%&'+/=?^_`{|}~-]+(?:" +
            "\\.[a-zA-Z0-9!#$%&'+/=?^_`{|}~-]+)|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b" +
            "\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-][a-zA-Z0-9])?" +
            "\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-][a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-" +
            "\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            btnLogin = v.findViewById(R.id.btnLogin);
            btnRegistro = v.findViewById(R.id.btnRegistro);



        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().isEmpty() || etSenha.getText().toString().isEmpty()){
                    mensagem("Campo Obrigatório", "Atenção");
                }

                //card após logar
                CardFragment cf = new CardFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.moldura, cf).commit();

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

}
