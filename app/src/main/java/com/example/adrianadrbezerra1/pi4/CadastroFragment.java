package com.example.adrianadrbezerra1.pi4;


import android.app.AlertDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment {
    private EditText etNome;
    private EditText etSobrenome;
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
        View v =  inflater.inflate(R.layout.fragment_cadastro, container, false);
            etNome = v.findViewById(R.id.etNome);
            etSobrenome = v.findViewById(R.id.etSobrenome);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            etConfSenha = v.findViewById(R.id.etConfSenha);
            radioFem = v.findViewById(R.id.radioFem);
            radioMasc = v.findViewById(R.id.radioMasc);
            btnRegistro = v.findViewById(R.id.btnRegistro);

            View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(etNome.getText().toString().isEmpty() || etSobrenome.getText().toString().isEmpty()
                        || etEmail.getText().toString().isEmpty() || etSenha.getText().toString().isEmpty()){
                    mensagem("Campo Obrigatório", "Atenção");

                }
                if(!etSenha.getText().toString().equalsIgnoreCase(etConfSenha.getText().toString())){
                    mensagem("Senha incorreta", "Atenção");
                }
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
