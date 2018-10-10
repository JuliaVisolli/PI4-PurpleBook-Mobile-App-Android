package com.example.adrianadrbezerra1.pi4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private Button btnRegistro;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);;
        btnRegistro = v.findViewById(R.id.btnRegistro);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //criando fragmento de cadastro
                CadastroFragment cf = new CadastroFragment();
                //fazendo tran
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, cf).commit();
            }
        };
        btnRegistro.setOnClickListener(listener);

        return v;


    }

}
