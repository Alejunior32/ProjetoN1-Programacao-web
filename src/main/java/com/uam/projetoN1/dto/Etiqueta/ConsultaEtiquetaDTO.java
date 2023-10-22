package com.uam.projetoN1.dto.Etiqueta;

import com.gft.Desafio_noticias_rapidas.dto.Usuario.ConsultaUsuarioSemEtiquetasDTO;

import java.util.List;

public class ConsultaEtiquetaDTO {

    private Long id;

    private String nomeEtiqueta;

    private List<ConsultaUsuarioSemEtiquetasDTO> usuarios;

    public ConsultaEtiquetaDTO() {
    }

    public ConsultaEtiquetaDTO(Long id, String nomeEtiqueta, List<ConsultaUsuarioSemEtiquetasDTO> usuarios) {
        this.id = id;
        this.nomeEtiqueta = nomeEtiqueta;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEtiqueta() {
        return nomeEtiqueta;
    }

    public void setNomeEtiqueta(String nomeEtiqueta) {
        this.nomeEtiqueta = nomeEtiqueta;
    }

    public List<ConsultaUsuarioSemEtiquetasDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ConsultaUsuarioSemEtiquetasDTO> usuarios) {
        this.usuarios = usuarios;
    }
}