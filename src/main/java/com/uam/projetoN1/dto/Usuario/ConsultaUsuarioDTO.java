package com.uam.projetoN1.dto.Usuario;

import com.gft.Desafio_noticias_rapidas.dto.Etiqueta.ConsultaEtiquetaSemUsuariosDTO;

import java.util.List;

public class ConsultaUsuarioDTO {


    private Long id;

    private String email;

    private String senha;

    private List<ConsultaEtiquetaSemUsuariosDTO> etiquetas;

    public ConsultaUsuarioDTO() {
    }

    public ConsultaUsuarioDTO(Long id, String email, String senha, List<ConsultaEtiquetaSemUsuariosDTO> etiquetas) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.etiquetas = etiquetas;
    }

    public List<ConsultaEtiquetaSemUsuariosDTO> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<ConsultaEtiquetaSemUsuariosDTO> etiquetas) {
        this.etiquetas = etiquetas;
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
