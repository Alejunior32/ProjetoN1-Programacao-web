package com.uam.projetoN1.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaUsuarioSemEtiquetasDTO {

    private Long id;

    private String email;

    private String senha;


}
