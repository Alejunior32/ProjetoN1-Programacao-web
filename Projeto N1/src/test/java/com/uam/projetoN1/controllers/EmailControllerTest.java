package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Noticia.ListaNoticiasDTO;
import com.uam.projetoN1.dto.Noticia.NoticiaDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Historico;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.EmailSenderService;
import com.uam.projetoN1.services.EtiquetaService;
import com.uam.projetoN1.services.NoticiaService;
import com.uam.projetoN1.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private NoticiaService noticiaService;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EtiquetaService etiquetaService;

    NoticiaDTO noticiaDTO;

    Usuario usuario;

    Etiqueta etiqueta;
    @BeforeEach
    public void setUp() {
        openMocks(this);
        start();
    }

    @Test
    public void testEnviarEmail() {
        Long usuarioId = 1L;
        List<Etiqueta> etiquetas =List.of(etiqueta);
        List<String> nomeEtiquetas = List.of(etiqueta.getNomeEtiqueta());
        List<ListaNoticiasDTO> noticias = List.of(new ListaNoticiasDTO(List.of(noticiaDTO).size(), List.of(noticiaDTO)));

        when(usuarioService.buscarUsuarioPeloPerfil(usuarioId)).thenReturn(usuario);
        when(etiquetaService.buscarEtiquetasDoUsuarioLista(usuario)).thenReturn(etiquetas);


        when(noticiaService.noticiasDataAtual(nomeEtiquetas)).thenReturn(noticias);

        ResponseEntity<?> response = emailController.enviarEmail(usuarioId);

        assertEquals(200, response.getStatusCodeValue());
        verify(emailSenderService, times(1)).EnviarEmail(usuario.getEmail(), "Notícias diária", noticias.toString());
    }

    private void start() {
        etiqueta = new Etiqueta(1L, "NOME_ETIQUETA", null);
        usuario = new Usuario(1l, "email", "senha", List.of(etiqueta), null);
        noticiaDTO = new NoticiaDTO("test","introducao","data","editorias","imagens");
    }
}