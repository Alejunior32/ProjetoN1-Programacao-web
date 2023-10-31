package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Acesso.ConsultaAcessosDTO;
import com.uam.projetoN1.dto.Historico.ConsultaHistoricoDTO;
import com.uam.projetoN1.dto.Noticia.ListaNoticiasDTO;
import com.uam.projetoN1.dto.Noticia.NoticiaDTO;
import com.uam.projetoN1.dto.Usuario.RegistroUsuarioDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Historico;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class HistoricoControllerTest {

    @InjectMocks
    private HistoricoController historicoController;

    @Mock
    private HistoricoService historicoService;

    @Mock
    private AutenticacaoService autenticacaoService;
    ConsultaAcessosDTO consultaAcessosDTO;
    Historico historico;

    Usuario usuario;

    Etiqueta etiqueta;
    @BeforeEach
    public void setUp() {
        openMocks(this);
        start();
    }

    @Test
    public void testBuscarHistoricoUsuario() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Long usuarioId = 1L;
        List<Historico> historicoDTOList = List.of(historico);

        when(autenticacaoService.retornarIdUsuario(any())).thenReturn(usuarioId);
        when(historicoService.buscarHistoricoPeloUsuario(usuarioId)).thenReturn(historicoDTOList);

        ResponseEntity<List<ConsultaHistoricoDTO>> response = historicoController.buscarHistoricoUsuario(request);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarEtiquetasMaisAcessadas() {
        List<ConsultaAcessosDTO> acessosDTOList = List.of(consultaAcessosDTO);

        when(historicoService.historicoEtiquetaMaisAcessado()).thenReturn(acessosDTOList);

        ResponseEntity<List<ConsultaAcessosDTO>> response = historicoController.buscarEtiquetasMaisAcessadas();

        assertEquals(200, response.getStatusCodeValue());
    }

    private void start(){
        etiqueta = new Etiqueta(1L, "NOME_ETIQUETA",null);
        usuario = new Usuario(1l,"email","senha", List.of(etiqueta),null);
        historico = new Historico(1L,usuario,etiqueta,"data");
        consultaAcessosDTO = new ConsultaAcessosDTO("etiqueta",1);
    }
}