package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Etiqueta.ConsultaEtiquetaDTO;
import com.uam.projetoN1.dto.Etiqueta.ConsultaEtiquetaSemUsuariosDTO;
import com.uam.projetoN1.dto.Etiqueta.RegistroEtiquetaDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.AutenticacaoService;
import com.uam.projetoN1.services.EtiquetaService;
import com.uam.projetoN1.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class NoticiaControllerTest {


    @InjectMocks
    private EtiquetaController etiquetaController;

    @Mock
    private EtiquetaService etiquetaService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AutenticacaoService autenticacaoService;

    RegistroEtiquetaDTO etiquetaDTO;

    Usuario usuario;

    Etiqueta etiqueta;
    @BeforeEach
    public void setUp() {
        openMocks(this);
        start();
    }

    @Test
    public void testSalvarEtiquetaParaUsuario() {

        HttpServletRequest request = mock(HttpServletRequest.class);


        when(etiquetaService.verificarExistenciaEtiqueta(any())).thenReturn(etiqueta);
        when(autenticacaoService.retornarIdUsuario(any())).thenReturn(1L);
        ResponseEntity<ConsultaEtiquetaSemUsuariosDTO> response = etiquetaController.salvarEtiquetaParaUsuario(request, etiquetaDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarEtiquetasDoUsuario() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Pageable pageable = Pageable.unpaged();

        Page<Etiqueta> etiquetaPage = new PageImpl<>(List.of(etiqueta), pageable, 0);

        when(autenticacaoService.retornarIdUsuario(any())).thenReturn(1L);
        when(usuarioService.buscarUsuarioPeloPerfil(1L)).thenReturn(usuario);
        when(etiquetaService.buscarEtiquetasDoUsuario(any(), any())).thenReturn(etiquetaPage);

        ResponseEntity<Page<ConsultaEtiquetaSemUsuariosDTO>> response = etiquetaController.buscarEtiquetasDoUsuario(request, pageable);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarEtiquetasPeloAdmin() {
        Pageable pageable = Pageable.unpaged();
        Page<Etiqueta> etiquetaPage = new PageImpl<>(List.of(etiqueta), pageable, 0);

        when(etiquetaService.buscarTodasEtiquetas(any())).thenReturn(etiquetaPage);
        ResponseEntity<Page<ConsultaEtiquetaDTO>> response = etiquetaController.buscarEtiquetasPeloAdmin(pageable);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarEtiquetaPeloAdmin() {
        Long etiquetaId = 1L;

        when(etiquetaService.buscarEtiqueta(etiquetaId)).thenReturn(etiqueta);
        ResponseEntity<ConsultaEtiquetaDTO> response = etiquetaController.buscarEtiquetaPeloAdmin(etiquetaId);

        assertEquals(200, response.getStatusCodeValue());
    }

    private void start(){
        usuario = new Usuario(1l,"email","senha", null,null);
        etiqueta = new Etiqueta(1L, "NOME_ETIQUETA", List.of(usuario));
        etiquetaDTO = new RegistroEtiquetaDTO("nome");
    }
}