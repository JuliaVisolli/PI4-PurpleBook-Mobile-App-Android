package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCurtida {

    @POST("/WSEcommerce/rest/curtida")
    Call<CurtidaDTO> saveCurtida(@Body CurtidaDTO curtida);

    @GET("/WSEcommerce/rest/count/{param}")
    Call<UsuarioDTO> getCountAllComentariosByIDHistoria(@Path("param") String idHistoria);
}
