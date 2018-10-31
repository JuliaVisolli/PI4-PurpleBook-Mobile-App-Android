package com.example.littlewolf_pc.app.model;

import java.util.Date;

public class HistoriaDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String texto;
    private byte[] foto;
    private Date data;
    private Integer totalComentarios;
    private Integer totalCurtidas;

    public HistoriaDTO() {
        super();
    }

    public HistoriaDTO(Long id) {
        this();
        this.id = id;
    }

    public HistoriaDTO(Long id, String texto, Date data, int totalComentarios, int totalCurtidas) {
        this();
        this.id = id;
        this.texto = texto;
        this.data = data;
        this.totalComentarios = totalComentarios;
        this.totalCurtidas = totalCurtidas;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(Integer totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

    public Integer getTotalCurtidas() {
        return totalCurtidas;
    }

    public void setTotalCurtidas(Integer totalCurtidas) {
        this.totalCurtidas = totalCurtidas;
    }
}
