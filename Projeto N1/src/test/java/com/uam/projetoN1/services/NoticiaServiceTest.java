package com.uam.projetoN1.services;

import com.uam.projetoN1.dto.Noticia.ListaNoticiasDTO;
import com.uam.projetoN1.dto.Noticia.NoticiaDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Perfil;
import com.uam.projetoN1.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class NoticiaServiceTest {

    @Mock
    WebClient webClient;
    @InjectMocks
    NoticiaService noticiaService;

    NoticiaDTO noticiaDTO;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void noticiaComData() {


    }

    @Test
    void noticiaDataAtual() {
    }

    @Test
    void noticiasComData() {
    }

    @Test
    void noticiasDataAtual() {
    }

    private void start(){
        noticiaDTO = new NoticiaDTO("test","introducao","data","editorias","imagens");
    }
}