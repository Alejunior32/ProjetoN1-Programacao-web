package com.uam.projetoN1.dto.Etiqueta;

public class RegistroEtiquetaDTO {

    private String nomeEtiqueta;

    public RegistroEtiquetaDTO(String nomeEtiqueta) {
        this.nomeEtiqueta = nomeEtiqueta;
    }

    public RegistroEtiquetaDTO() {
    }

    public String getNomeEtiqueta() {
        return nomeEtiqueta;
    }

    public void setNomeEtiqueta(String nomeEtiqueta) {
        this.nomeEtiqueta = nomeEtiqueta;
    }
}
