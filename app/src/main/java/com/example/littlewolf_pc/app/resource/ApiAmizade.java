package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.AmizadeDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiAmizade {

    @POST("/WSEcommerce/rest/amizade")
    Call<AmizadeDTO> solicitaAmizade(@Body AmizadeDTO amizade);

    @DELETE("/WSEcommerce/rest/amizade/{usuario1}/{usuario2}")
    Call<Integer>recusarAmizade(@Path("usuario1") Integer idUsuario1, @Path("usuario2") Integer idUsuario2);
}
