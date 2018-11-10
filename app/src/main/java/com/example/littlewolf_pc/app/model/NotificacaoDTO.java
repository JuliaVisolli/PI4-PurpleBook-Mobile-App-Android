package com.example.littlewolf_pc.app.model;

import java.util.Date;

public class NotificacaoDTO {

     private Integer id;
     private String notificacao;
     private Date data;

     public NotificacaoDTO(Integer id, String notificacao, Date data) {
          this.id = id;
          this.notificacao = notificacao;
          this.data = data;
     }

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getNotificacao() {
          return notificacao;
     }

     public void setNotificacao(String notificacao) {
          this.notificacao = notificacao;
     }

     public Date getData() {
          return data;
     }

     public void setData(Date data) {
          this.data = data;
     }
}
