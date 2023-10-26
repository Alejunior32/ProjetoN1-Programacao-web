package com.uam.projetoN1.dto.Administrador;

public class ConsultaAdminDTO {


    private Long id;

    private String email;

    private String senha;

    public ConsultaAdminDTO() {
    }

    public ConsultaAdminDTO(Long id, String email, String senha) {
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
