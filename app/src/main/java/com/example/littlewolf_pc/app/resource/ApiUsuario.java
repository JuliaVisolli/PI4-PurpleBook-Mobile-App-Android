package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.UsuarioDTO;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiUsuario {

    @POST("/WSEcommerce/rest/usuario/login")
    Call<UsuarioDTO> login(@Body UsuarioDTO usuarioDTO);

    @GET("/WSEcommerce/rest/usuario/{usuarioid}")
    Call<UsuarioDTO> selectUsuario(@Path("usuarioid") String id);

    @POST("/WSEcommerce/rest/usuario")
    Call<UsuarioDTO> saveUsuario(@Body UsuarioDTO usuarioDTO);

    @GET("/WSEcommerce/rest/usuario")
    Call<List<UsuarioDTO>> selectAllUsuario();

    @GET("/WSEcommerce/rest/usuario/image/{param}")
    Call<byte[]> selectAllUsuario(@Path("param") String usuarioId);

    @GET("/WSEcommerce/rest/usuario/amigo/{param}")
    Call<List<UsuarioDTO>> buscaAmigo(@Path("param") String usuarioId);

    @GET("/WSEcommerce/rest/usuario/perfil/{param}")
    Call<List<UsuarioDTO>> perfilUsuario(@Path("param") String usuarioId);


}
