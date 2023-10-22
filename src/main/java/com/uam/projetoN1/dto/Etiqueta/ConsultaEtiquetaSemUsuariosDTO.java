package com.uam.projetoN1.dto.Etiqueta;

public class ConsultaEtiquetaSemUsuariosDTO {

    private Long id;

    private String nomeEtiqueta;

    public ConsultaEtiquetaSemUsuariosDTO() {
    }

    public ConsultaEtiquetaSemUsuariosDTO(Long id, String nomeEtiqueta) {
        this.id = id;
        this.nomeEtiqueta = nomeEtiqueta;
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
}
