package com.example.littlewolf_pc.app.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.littlewolf_pc.app.R;
import com.example.littlewolf_pc.app.model.UsuarioDTO;
import com.example.littlewolf_pc.app.resource.ApiUsuario;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
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
    ImageView imagem;
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_cadastro, container, false);
        final Dialog loading = new Dialog(getContext(), android.R.style.Theme_Black);
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading.setContentView(viewDialog);
        loading.setCancelable(false);

            etNome = v.findViewById(R.id.etNome);
            etEmail = v.findViewById(R.id.etEmail);
            etSenha = v.findViewById(R.id.etSenha);
            etConfSenha = v.findViewById(R.id.etConfSenha);
            btnRegistro = v.findViewById(R.id.btnRegistro);
            btnLogar = v.findViewById(R.id.btnLogar);
            imagem = v.findViewById(R.id.user_imagem_cadastrar);



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

                loading.show();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://josiasveras.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


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
                            loading.dismiss();
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
                        loading.dismiss();
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

        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_IMAGE_CAPTURE = 51;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int REQUEST_IMAGE_CAPTURE = 51;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
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

            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resized);
            roundedBitmapDrawable.setCircular(true);
            imagem.setImageDrawable(roundedBitmapDrawable);



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String imagemEmBase64 = Base64.encodeToString(byteArray,Base64.NO_WRAP);
            usuarioDTO.setFoto(imagemEmBase64);



        }
    }

}
