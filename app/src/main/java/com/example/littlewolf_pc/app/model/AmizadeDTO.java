package com.example.littlewolf_pc.app.model;

public class AmizadeDTO {

    private UsuarioDTO usuario1;
    private UsuarioDTO usuario2;
    private boolean aprovada = false;

    public AmizadeDTO() {
        super();
    }

    public AmizadeDTO(UsuarioDTO usuario1, UsuarioDTO usuario2, boolean aprovada) {
        this();
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.aprovada = aprovada;
    }

    public UsuarioDTO getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(UsuarioDTO usuario1) {
        this.usuario1 = usuario1;
    }

    public UsuarioDTO getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(UsuarioDTO usuario2) {
        this.usuario2 = usuario2;
    }

    public boolean isAprovada() {
        return aprovada;
    }

    public void setAprovada(boolean aprovada) {
        this.aprovada = aprovada;
    }
}
