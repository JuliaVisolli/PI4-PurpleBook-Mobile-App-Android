package com.example.adrianadrbezerra1.pi4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecsenhaActivity extends AppCompatActivity {
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recsenha);


        btnEnviar = findViewById(R.id.btnEnviar);

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        };
        btnEnviar.setOnClickListener(listener);
    }
}
