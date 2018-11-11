package com.example.littlewolf_pc.app.model;

public class CurtidaDTO {

    private UsuarioDTO usuario;
    private HistoriaDTO historia;

    public CurtidaDTO() {
        super();
    }

    public CurtidaDTO(UsuarioDTO usuario, HistoriaDTO historia) {
        this();
        this.usuario = usuario;
        this.historia = historia;
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
