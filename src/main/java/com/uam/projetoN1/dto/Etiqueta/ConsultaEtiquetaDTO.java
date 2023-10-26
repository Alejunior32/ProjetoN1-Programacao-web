package com.uam.projetoN1.dto.Etiqueta;

import com.uam.projetoN1.dto.Usuario.ConsultaUsuarioSemEtiquetasDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEtiquetaDTO {

    private Long id;

    private String nomeEtiqueta;

    private List<ConsultaUsuarioSemEtiquetasDTO> usuarios;

}