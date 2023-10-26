package com.uam.projetoN1.dto.Noticia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaNoticiasDTO {

    private Integer count;

    private List<NoticiaDTO> items;

    @Override
    public String toString() {
        return "ListaNoticiasDTO{" +
                "count=" + count +'\''+
                ", items=" + items +'\''+
                '}';
    }
}
