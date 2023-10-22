package com.uam.projetoN1.dto.Usuario;

public class ConsultaUsuarioSemEtiquetasDTO {

    private Long id;

    private String email;

    private String senha;

    public ConsultaUsuarioSemEtiquetasDTO() {
    }

    public ConsultaUsuarioSemEtiquetasDTO(Long id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
