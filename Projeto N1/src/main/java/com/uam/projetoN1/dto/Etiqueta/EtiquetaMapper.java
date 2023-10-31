package com.uam.projetoN1.dto.Etiqueta;

import com.uam.projetoN1.dto.Usuario.UsuarioMapper;
import com.uam.projetoN1.entities.Etiqueta;

import java.util.stream.Collectors;

public class EtiquetaMapper {

    public static Etiqueta fromDTO(RegistroEtiquetaDTO dto){
        return new Etiqueta(null, dto.getNomeEtiqueta(),null);
    }

    public static ConsultaEtiquetaSemUsuariosDTO fromEntityToEtiquetaSemUsuario(Etiqueta etiqueta){
        return new ConsultaEtiquetaSemUsuariosDTO(etiqueta.getId(),etiqueta.getNomeEtiqueta());
    }

    public static ConsultaEtiquetaDTO fromEntityToConsultaDTO(Etiqueta etiqueta){
        return new ConsultaEtiquetaDTO(etiqueta.getId(), etiqueta.getNomeEtiqueta(),  etiqueta.getUsuarios()
                .stream().map(UsuarioMapper::fromEntityToConsultaSemEtiquetaDTO).collect(Collectors.toList()));
    }
}
