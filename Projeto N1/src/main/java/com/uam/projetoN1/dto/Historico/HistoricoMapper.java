package com.uam.projetoN1.dto.Historico;

import com.uam.projetoN1.entities.Historico;

public class HistoricoMapper {

    public static ConsultaHistoricoDTO fromEntity(Historico historico){
        return new ConsultaHistoricoDTO(historico.getEtiqueta().getNomeEtiqueta(), historico.getData());
    }
}
