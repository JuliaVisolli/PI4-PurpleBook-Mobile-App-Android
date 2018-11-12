package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.ComentarioDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiComentario {

    @POST("/WSEcommerce/rest/comentario")
    Call<ComentarioDTO> saveComentario(@Body ComentarioDTO comentario);

    @GET("/WSEcommerce/rest/comentario/historia/{param}")
    Call<List<ComentarioDTO>> getAllComentariosByIdHistoria(@Path("param") String idHistoria);

    @GET("/WSEcommerce/rest/comentario/count/{param}")
    Call<Integer> getCountAllComentariosByIDHistoria(@Path("param") String idHistoria);
}
