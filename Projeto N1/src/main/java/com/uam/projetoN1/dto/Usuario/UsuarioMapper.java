package com.uam.projetoN1.dto.Usuario;

import com.uam.projetoN1.dto.Etiqueta.EtiquetaMapper;
import com.uam.projetoN1.entities.Perfil;
import com.uam.projetoN1.entities.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
