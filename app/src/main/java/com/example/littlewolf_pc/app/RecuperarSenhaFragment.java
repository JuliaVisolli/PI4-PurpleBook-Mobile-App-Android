package com.example.littlewolf_pc.app;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecuperarSenhaFragment extends Fragment {
    private TextView etEmail;
    private Button btnEnviar;

    public RecuperarSenhaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_recuperar_senha, container, false);

            etEmail = v.findViewById(R.id.etEmail);
            btnEnviar = v.findViewById(R.id.btnEnviar);

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().isEmpty()){
                    mensagem("Campo Obrigatório", "Atenção");
                }
            }
        };
        btnEnviar.setOnClickListener(listener);

        return v;
    }
    private void mensagem(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder (RecuperarSenhaFragment.this.getActivity());
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
