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

    @DELETE("/WSEcommerce/rest/amizade/{idUsuario1}/{idUsuario2}/{aprovada}")
    Call<AmizadeDTO>deleteCurtida(@Path("idUsuario2") Integer idUsuario1, @Path("idUsuario2") Integer idUsuario2, @Path("aprovada") Boolean aprovada);
}
