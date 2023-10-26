package com.uam.projetoN1.util;

import com.uam.projetoN1.dto.Acesso.ConsultaAcessosDTO;

import java.util.Comparator;

public class SortByAcesso implements Comparator<ConsultaAcessosDTO> {

    @Override
    public int compare(ConsultaAcessosDTO o1, ConsultaAcessosDTO o2) {
        return o2.getAcessos() - o1.getAcessos();
    }
}
