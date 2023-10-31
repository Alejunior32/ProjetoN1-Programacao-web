package com.uam.projetoN1.services;

import com.uam.projetoN1.dto.Autenticacao.AutenticacaoDTO;
import com.uam.projetoN1.dto.Autenticacao.TokenDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Perfil;
import com.uam.projetoN1.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AutenticacaoServiceTest {

    public static final long ID = 1L;
    public static final String NOME_ETIQUETA = "corinthians";
    public static final int INDEX = 0;
    public static final String EMAIL = "test@email.com";
    public static final String SENHA = "1234";

    @Mock
    AuthenticationManager authManager;

    @Mock
    Authentication authentication;

    @InjectMocks
    AutenticacaoService autenticacaoService;
    Usuario usuario;
    private Etiqueta etiqueta;
    private Perfil perfil;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        start();
    }

    @Test
    void testAutenticar() {
        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);

        AutenticacaoDTO authForm = new AutenticacaoDTO("example@email.com", "senha");
        TokenDTO tokenDTO = autenticacaoService.autenticar(authForm);

        assertNotNull(tokenDTO);
    }

    @Test
    void testVerificaTokenValido() {
        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);

        AutenticacaoDTO authForm = new AutenticacaoDTO("example@email.com", "senha");
        String token = autenticacaoService.autenticar(authForm).getToken();
        assertTrue(autenticacaoService.verificaToken(token));
    }

    @Test
    public void testVerificaTokenInvalido() {
        String token = "token-invalido";
        assertFalse(autenticacaoService.verificaToken(token));
    }

    @Test
    void retornarIdUsuario() {

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);

        AutenticacaoDTO authForm = new AutenticacaoDTO("example@email.com", "senha");
        String token = autenticacaoService.autenticar(authForm).getToken();
        System.out.println(token);
        Long userId = autenticacaoService.retornarIdUsuario(token);

        assertEquals(Long.valueOf(1), userId);
    }

    @Test
    void getToken() {

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);

        AutenticacaoDTO authForm = new AutenticacaoDTO("example@email.com", "senha");
        String tokenValido = autenticacaoService.autenticar(authForm).getToken();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer "+tokenValido);
        String tokenTest = autenticacaoService.getToken(request);
        assertEquals(tokenValido, tokenTest);
    }

    private void start(){
        ReflectionTestUtils.setField(autenticacaoService,"secret","*Ab*V(Sxtt}v'[GEI{FYkwm;-{Por#,d)I)i>u08Ae~G=Ec!wS2=NGhTQe6},R#");
        ReflectionTestUtils.setField(autenticacaoService,"expiration","600000");
        ReflectionTestUtils.setField(autenticacaoService,"issuer","test");

        etiqueta = new Etiqueta(ID, NOME_ETIQUETA,null);
        perfil = new Perfil(ID,"USUARIO");
        usuario = new Usuario(ID, EMAIL, SENHA, List.of(etiqueta),perfil);
    }
}