package com.uam.projetoN1.dto.Usuario;

import com.gft.Desafio_noticias_rapidas.dto.Etiqueta.EtiquetaMapper;
import com.gft.Desafio_noticias_rapidas.entities.Perfil;
import com.gft.Desafio_noticias_rapidas.entities.Usuario;

import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario fromDTO(RegistroUsuarioDTO dto){
        Perfil perfil = new Perfil();
        perfil.setId(2L);
        return new Usuario(null, dto.getEmail(), dto.getSenha(),null,perfil);
    }

    public static ConsultaUsuarioDTO fromEtityToConsultaDTO(Usuario usuario){
        return new ConsultaUsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getSenha(),usuario.getEtiquetas()
                .stream().map(EtiquetaMapper::fromEntityToEtiquetaSemUsuario).collect(Collectors.toList()));
    }

    public static ConsultaUsuarioSemEtiquetasDTO fromEntityToConsultaSemEtiquetaDTO(Usuario usuario){
        return new ConsultaUsuarioSemEtiquetasDTO(usuario.getId(), usuario.getEmail(), usuario.getSenha());
    }

}
