package com.example.littlewolf_pc.app.model;


import java.util.Date;

public class ComentarioDTO {

    private Integer id;
    private String comentario;
    private Date data;

    public ComentarioDTO(Integer id, String comentario, Date data){
        this.id = id;
        this.comentario = comentario;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
