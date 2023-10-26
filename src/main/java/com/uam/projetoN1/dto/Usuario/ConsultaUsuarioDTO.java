package com.uam.projetoN1.dto.Usuario;

import com.uam.projetoN1.dto.Etiqueta.ConsultaEtiquetaSemUsuariosDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaUsuarioDTO {


    private Long id;

    private String email;

    private String senha;

    private List<ConsultaEtiquetaSemUsuariosDTO> etiquetas;


}
