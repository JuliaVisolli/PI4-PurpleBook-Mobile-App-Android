package com.example.littlewolf_pc.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.littlewolf_pc.app.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.ByteArrayOutputStream;

public class HistoriaActivity extends AppCompatActivity {
    ImageButton btnFotoHistoria;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        imageView = findViewById(R.id.user_img2);
        btnFotoHistoria = findViewById(R.id.btnFotoHistoria);

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




        }
    }
}
