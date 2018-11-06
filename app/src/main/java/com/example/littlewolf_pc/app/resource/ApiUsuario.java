package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.UsuarioDTO;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiUsuario {

    @POST("/WSEcommerce/rest/usuario/login")
    Call<UsuarioDTO> login(@Body UsuarioDTO usuarioDTO);

    @GET("/WSEcommerce/rest/usuario/{usuarioid}")
    Call<UsuarioDTO> getUsuario(@Path("usuarioid") Integer id);

    @POST("/WSEcommerce/rest/usuario")
    Call<UsuarioDTO> saveUsuario(@Body UsuarioDTO usuarioDTO);
}
