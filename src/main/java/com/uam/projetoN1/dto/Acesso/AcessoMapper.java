package com.uam.projetoN1.dto.Acesso;


import com.uam.projetoN1.entities.Acesso;

public class AcessoMapper {

    public static ConsultaAcessosDTO fromEntityToMaisAcessados(Acesso acesso){
        return new ConsultaAcessosDTO(acesso.getEtiqueta().getNomeEtiqueta(), acesso.getAcessos());
    }


}
