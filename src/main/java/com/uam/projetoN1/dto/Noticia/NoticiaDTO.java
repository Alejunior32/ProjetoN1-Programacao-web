package com.uam.projetoN1.dto.Noticia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaDTO {

    private String titulo;
    private String introducao;
    private String data_publicacao;
    private String editorias;
    private String imagens;

    @Override
    public String toString() {
        return "NoticiaDTO{" +
                "Titulo='" + titulo + '\'' +
                ", Introdução='" + introducao + '\'' +
                ", data_publicacao='" + data_publicacao + '\'' +
                ",editorias='"+ editorias + '\'' +
                ", imagens='" + imagens + '\'' +
                '}';
    }
}
