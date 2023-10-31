package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Usuario.ConsultaUsuarioDTO;
import com.uam.projetoN1.dto.Usuario.ConsultaUsuarioSemEtiquetasDTO;
import com.uam.projetoN1.dto.Usuario.RegistroUsuarioDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.EmailSenderService;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EtiquetaService etiquetaService;

    @Mock
    private EmailSenderService emailSenderService;

    RegistroUsuarioDTO usuarioDTO;
    Usuario usuario;

    Etiqueta etiqueta;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        start();
    }

    @Test
    public void testSalvarUsuario() {

        when(usuarioService.salvarUsuario(any())).thenReturn(usuario);
        ResponseEntity<ConsultaUsuarioSemEtiquetasDTO> response = usuarioController.salvarUsuario(usuarioDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(emailSenderService, times(1)).EnviarEmail(eq(usuario.getEmail()), anyString(), anyString());
    }

    @Test
    public void testBuscarUsuario() {

        when(usuarioService.buscarUsuarioPeloPerfil(anyLong())).thenReturn(usuario);
        ResponseEntity<ConsultaUsuarioDTO> response = usuarioController.buscarUsuario(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBuscarTodosUsuarios() {
        Pageable pageable = Pageable.unpaged();
        Page<Usuario> usuarioPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(usuarioService.buscarTodosUsuarios(pageable)).thenReturn(usuarioPage);
        ResponseEntity<Page<ConsultaUsuarioDTO>> response = usuarioController.buscarTodosUsuarios(pageable);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testAtualizarUmUsuario() {
        Long userId = 1L;

        when(usuarioService.atualizarUsuario(any(), eq(userId))).thenReturn(usuario);
        ResponseEntity<ConsultaUsuarioSemEtiquetasDTO> response = usuarioController.atualizarUmUsuario(userId, usuarioDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeletarUsuario() {
        Long userId = 1L;

        doNothing().when(usuarioService).deletarUsuario(userId);
        ResponseEntity<ConsultaUsuarioDTO> response = usuarioController.deletarUsuario(userId);

        assertEquals(200, response.getStatusCodeValue());
    }

    private void start(){
        etiqueta = new Etiqueta(1L, "NOME_ETIQUETA",null);
        usuario = new Usuario(1l,"email","senha", List.of(etiqueta),null);
        usuarioDTO = new RegistroUsuarioDTO("email","senha");
    }
}