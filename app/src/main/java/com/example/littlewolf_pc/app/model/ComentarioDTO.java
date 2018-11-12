package com.example.littlewolf_pc.app.model;


import java.util.Date;

public class ComentarioDTO {

    private Integer id;
    private UsuarioDTO usuario;
    private HistoriaDTO historia;
    private String texto;
    private Date data;

    public ComentarioDTO(Integer id, String texto, Date data){
        this.id = id;
        this.texto = texto;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public HistoriaDTO getHistoria() {
        return historia;
    }

    public void setHistoria(HistoriaDTO historia) {
        this.historia = historia;
    }
}
