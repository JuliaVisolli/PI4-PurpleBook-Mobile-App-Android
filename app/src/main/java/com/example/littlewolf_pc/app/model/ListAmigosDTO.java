package com.example.littlewolf_pc.app.model;


public class ListAmigosDTO {

    private Integer id;
    private String listAmigos;

    public ListAmigosDTO(Integer id, String listAmigos) {
        this.id = id;
        this.listAmigos = listAmigos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListAmigos() {
        return listAmigos;
    }

    public void setListAmigos(String listAmigos) {
        this.listAmigos = listAmigos;
    }

}
