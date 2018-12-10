package com.example.littlewolf_pc.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiCurtida;
import com.example.littlewolf_pc.app.resource.ApiHistoria;
import com.example.littlewolf_pc.app.utils.UsuarioSingleton;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoriaActivity extends AppCompatActivity {
    ImageButton btnFotoHistoria;
    ImageView imageView;
    ImageButton btnPostarHistoria;
    EditText etHistoria;
    HistoriaDTO historiaDTO = new HistoriaDTO();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://josiasveras.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        imageView = findViewById(R.id.user_img2);
        btnFotoHistoria = findViewById(R.id.btnFotoHistoria);
        btnPostarHistoria = findViewById(R.id.btnPostar);
        etHistoria = findViewById(R.id.et_historia);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_IMAGE_CAPTURE = 51;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        };
        btnFotoHistoria.setOnClickListener(listener);

        View.OnClickListener listenerPostar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idUsuario = UsuarioSingleton.getInstance().getUsuario().getId();
                if (idUsuario != null) {
                    ApiHistoria apiHistoria = retrofit.create(ApiHistoria.class);

                    historiaDTO.setUsuario(new UsuarioDTO(idUsuario));
                    historiaDTO.setTexto(etHistoria.getText().toString());
                    Call<HistoriaDTO> curtidaDTOCall = apiHistoria.saveHistoria(historiaDTO);

                    Callback<HistoriaDTO> curtidaCallback = new Callback<HistoriaDTO>() {
                        @Override
                        public void onResponse(Call<HistoriaDTO> call, Response<HistoriaDTO> response) {
                            HistoriaDTO historia = response.body();

                            if (historia != null && response.code() == 200) {
                                Toast.makeText(HistoriaActivity.this, getResources().getString(R.string.post_success), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(HistoriaActivity.this, InternalActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<HistoriaDTO> call, Throwable t) {
                            t.printStackTrace();

                        }
                    };
                    curtidaDTOCall.enqueue(curtidaCallback);
                }
            }

        };
        btnPostarHistoria.setOnClickListener(listenerPostar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int REQUEST_IMAGE_CAPTURE = 51;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            int largura = imageBitmap.getWidth();
            int altura = imageBitmap.getWidth();
            if (largura > altura) {
                altura = altura/largura * 100;
                largura = 100;
            } else {
                largura = largura/altura * 100;
                altura = 100;
            }

            Bitmap resized = Bitmap.createScaledBitmap(imageBitmap, largura, altura, true);

            imageView.setImageBitmap(resized);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String imagemEmBase64 = Base64.encodeToString(byteArray,Base64.NO_WRAP);

            if(imagemEmBase64 != null){
                historiaDTO.setFoto(imagemEmBase64);
            } else {
                historiaDTO.setFoto(null);
            }


        }
    }
}
