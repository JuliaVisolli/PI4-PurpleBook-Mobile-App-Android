package com.example.adrianadrbezerra1.pi4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Date;

public class ActivityCadastro extends AppCompatActivity {
    private EditText etNome;
    private EditText etSobrenome;
    private EditText etEmail;
    private Date dtNasc;
    private RadioButton radioFem;
    private RadioButton radioMasc;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

         btnRegistro = findViewById(R.id.btnRegistro);


        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        };
        btnRegistro.setOnClickListener(listener);
    }
}
