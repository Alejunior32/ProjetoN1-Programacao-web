package com.uam.projetoN1.util;

import com.uam.projetoN1.entities.Historico;

import java.util.Comparator;

public class SortByData implements Comparator<Historico> {

    @Override
    public int compare(Historico o1, Historico o2) {
        return o2.getData().compareTo(o1.getData());
    }
}
