package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.CurtidaDTO;
import com.example.littlewolf_pc.app.model.HistoriaDTO;
import com.example.littlewolf_pc.app.model.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCurtida {

    @POST("/WSEcommerce/rest/usuario/curtida")
    Call<CurtidaDTO> saveCurtida(@Body UsuarioDTO usuarioDTO, HistoriaDTO historiaDTO);
}
