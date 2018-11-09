package com.example.littlewolf_pc.app.resource;

import com.example.littlewolf_pc.app.model.HistoriaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHistoria {

    @GET("/WSEcommerce/rest/historia")
    Call<List<HistoriaDTO>> selectAllHistorias();

}
