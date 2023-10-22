package com.uam.projetoN1.dto.Historico;

public class ConsultaHistoricoDTO {

    private String etiqueta;

    private String data;

    public ConsultaHistoricoDTO() {
    }

    public ConsultaHistoricoDTO(String etiqueta, String data) {
        this.etiqueta = etiqueta;
        this.data = data;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
