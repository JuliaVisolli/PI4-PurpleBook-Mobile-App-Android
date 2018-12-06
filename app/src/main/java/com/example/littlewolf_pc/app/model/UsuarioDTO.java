package com.example.littlewolf_pc.app.model;

public class UsuarioDTO {

        private Integer id;
        private String nome;
        private String email;
        private String senha;
        private String foto;
        private HistoriaDTO historia;

        public UsuarioDTO() {
            super();
        }

    public UsuarioDTO(Integer id, String nome) {
        this();
        this.id = id;
        this.nome = nome;
    }

    public UsuarioDTO(String nome, Integer id) {
        this();
        this.nome = nome;
        this.id = id;
    }

        public UsuarioDTO(Integer id) {
            super();
            this.id = id;
        }


        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }


        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }
        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getFoto() {
            return foto;
        }
        public void setFoto(String foto) {
            this.foto = foto;
        }


        public HistoriaDTO getHistoria() {
            return historia;
        }

        public void setHistoria(HistoriaDTO historia) {
            this.historia = historia;
        }

}
