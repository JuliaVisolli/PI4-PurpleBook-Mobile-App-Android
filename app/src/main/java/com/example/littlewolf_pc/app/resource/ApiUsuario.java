package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.UsuarioDTO;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiUsuario {

    @POST("/login")
    Call<UsuarioDTO> login(@Body UsuarioDTO usuarioDTO);
}
