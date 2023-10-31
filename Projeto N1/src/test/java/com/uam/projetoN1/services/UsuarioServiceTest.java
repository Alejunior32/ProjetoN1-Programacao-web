package com.uam.projetoN1.services;

import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Perfil;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.exceptions.EntityNotFoundException;
import com.uam.projetoN1.exceptions.UsuarioComEmailJaExisteException;
import com.uam.projetoN1.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UsuarioServiceTest {

    public static final long ID = 1L;
    public static final String NOME_ETIQUETA = "sp";
    public static final String EMAIL = "test@email.com";
    public static final String SENHA = "1234";
    public static final int INDEX = 0;
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Etiqueta etiqueta;
    private Usuario usuario;
    private Usuario usuarioAtualizado;
    private Usuario admin;
    private Optional<Usuario> op_usuario;
    private Optional<Usuario> op_admin;
    private Perfil perfil_usuario;
    private Perfil perfil_admin;

    private Page<Usuario> usuarioPage;
    private Page<Usuario> adminPage;
    private Pageable pageable;



    @BeforeEach
    public void setUp(){
        openMocks(this);
        startUser();
    }

    @Test
    public void deveSalvarUsuarioERetornarOUsuarioSalvo(){
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        Usuario resposta = usuarioService.salvarUsuario(usuario);
        assertUsuarioPadrao(resposta);
    }

    @Test
    public void deveSalvarUsuarioERetornarUsuarioComEmailJaExisteException(){
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);
        try{
            usuarioService.salvarUsuario(usuario);
        }catch (Exception exception){
            assertEquals(UsuarioComEmailJaExisteException.class,exception.getClass());
        }
    }

    @Test
    public void deveRetornarUsuarioPeloId(){
        when(usuarioRepository.findById(anyLong())).thenReturn(op_usuario);
        Usuario resposta = usuarioService.buscarUsuarioPeloID(ID);
        assertUsuarioPadrao(resposta);
    }

    @Test
    public void deveBuscarUsuarioPeloIdERetornarException(){
        when(usuarioRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Usuário não encontrado"));
        try{
            usuarioService.buscarUsuarioPeloPerfil(ID);
        }catch (RuntimeException exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
    }

    @Test
    public void deveBuscarUsuarioPeloIdEPerfil(){
        when(usuarioRepository.findByIdAndPerfil_nome(anyLong(),anyString())).thenReturn(op_usuario);
        Usuario resposta = usuarioService.buscarUsuarioPeloPerfil(ID);
        assertPerfilUsuario(resposta);
    }

    @Test
    public void deveBuscarUsuarioPeloIdEPerfilERetornarException(){
        when(usuarioRepository.findByIdAndPerfil_nome(anyLong(),anyString()))
                .thenThrow(new EntityNotFoundException("Usuário não encontrado"));
        try{
            usuarioService.buscarUsuarioPeloPerfil(ID);
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
    }

    @Test
    public void deveBuscarAdminPeloIdEPerfil(){
        when(usuarioRepository.findByIdAndPerfil_nome(anyLong(),anyString()))
                .thenReturn(op_admin);
        Usuario resposta = usuarioService.buscarAdminPeloPerfil(ID);
        assertPerfilAdmin(resposta);
    }

    @Test
    public void deveBuscarAdminPeloIdEPerfilERetornarException(){
        when(usuarioRepository.findByIdAndPerfil_nome(anyLong(),anyString()))
                .thenThrow(new EntityNotFoundException(""));
        try{
             usuarioService.buscarAdminPeloPerfil(ID);
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
    }

    @Test
    public void deveBuscarTodosUsuariosERetornarUmaPage(){
        when(usuarioRepository.findAllByPerfil_nome((Pageable) any(),anyString())).thenReturn(usuarioPage);
        Page<Usuario> resposta = usuarioService.buscarTodosUsuarios(pageable);
        assertPageUsuario(resposta);
    }

    @Test
    public void deveBuscarTodosAdminERetornarUmaPage(){
        when(usuarioRepository.findAllByPerfil_nome(any(),anyString())).thenReturn(adminPage);
        Page<Usuario> resposta = usuarioService.buscarTodosAdmins(pageable);
        assertPageAdmins(resposta);
    }

    @Test
    public  void deveBuscarUsuarioPorEmail(){
        when(usuarioRepository.findByEmail(anyString())).thenReturn(op_usuario);
        Usuario resposta = usuarioService.buscarUsuarioPorEmail(EMAIL);
        assertUsuarioPadrao(resposta);
    }

    @Test
    public void deveAtualizarUsuario(){
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(usuarioRepository.findById(ID)).thenReturn(op_usuario);
        Usuario resposta = usuarioService.atualizarUsuario(usuarioAtualizado,ID);
        assertUsuarioPadrao(resposta);
    }

    @Test
    public void deveChamarAtualizarUsuarioERetornarException(){
        when(usuarioRepository.findById(ID)).thenThrow(new EntityNotFoundException(""));
        try{
            usuarioService.atualizarUsuario(usuarioAtualizado,ID);
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
    }

    @Test
    public void deveChamarDeletarUsuario(){
        when(usuarioRepository.findById(anyLong())).thenReturn(op_usuario);
        doNothing().when(usuarioRepository).deleteById(anyLong());
        usuarioService.deletarUsuario(ID);
        verify(usuarioRepository,times(1)).delete(any());
    }

    private void assertPageAdmins(Page<Usuario> resposta) {
        assertEquals(1,resposta.getTotalElements());
        assertEquals(1,resposta.getTotalPages());
        assertEquals(Usuario.class,resposta.stream().findFirst().get().getClass());
        assertEquals(ID,resposta.stream().findFirst().get().getId());
        assertEquals(EMAIL,resposta.stream().toList().get(INDEX).getEmail());
        assertEquals(2L, resposta.stream().toList().get(INDEX).getPerfil().getId());
        assertEquals("ADMIN",resposta.stream().toList().get(INDEX).getPerfil().getNome());
        assertEquals(ID,resposta.stream().toList().get(INDEX).getEtiquetas().get(INDEX).getId());
        assertEquals(NOME_ETIQUETA,resposta.stream().findFirst().get().getEtiquetas().get(INDEX).getNomeEtiqueta());
        assertEquals(Etiqueta.class,resposta.stream().toList().get(INDEX).getEtiquetas().get(INDEX).getClass());
    }

    private void assertPageUsuario(Page<Usuario> resposta) {
        assertEquals(1,resposta.getTotalElements());
        assertEquals(1,resposta.getTotalPages());
        assertEquals(Usuario.class,resposta.stream().findFirst().get().getClass());
        assertEquals(ID,resposta.stream().findFirst().get().getId());
        assertEquals(EMAIL,resposta.stream().toList().get(INDEX).getEmail());
        assertEquals(1L, resposta.stream().toList().get(INDEX).getPerfil().getId());
        assertEquals("USUARIO",resposta.stream().toList().get(INDEX).getPerfil().getNome());
        assertEquals(ID,resposta.stream().toList().get(INDEX).getEtiquetas().get(INDEX).getId());
        assertEquals(NOME_ETIQUETA,resposta.stream().findFirst().get().getEtiquetas().get(INDEX).getNomeEtiqueta());
        assertEquals(Etiqueta.class,resposta.stream().toList().get(INDEX).getEtiquetas().get(INDEX).getClass());
    }

    private static void assertPerfilUsuario(Usuario resposta) {
        assertNotNull(resposta);
        assertEquals(Usuario.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
        assertEquals(EMAIL, resposta.getEmail());
        assertEquals(Etiqueta.class, resposta.getEtiquetas().get(INDEX).getClass());
        assertEquals(ID, resposta.getEtiquetas().get(INDEX).getId());
        assertEquals(NOME_ETIQUETA, resposta.getEtiquetas().get(INDEX).getNomeEtiqueta());
        assertEquals(1L, resposta.getPerfil().getId());
        assertEquals("USUARIO", resposta.getPerfil().getNome());
    }

    private static void assertPerfilAdmin(Usuario resposta) {
        assertNotNull(resposta);
        assertEquals(Usuario.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
        assertEquals(EMAIL, resposta.getEmail());
        assertEquals(Etiqueta.class, resposta.getEtiquetas().get(INDEX).getClass());
        assertEquals(ID, resposta.getEtiquetas().get(INDEX).getId());
        assertEquals(NOME_ETIQUETA, resposta.getEtiquetas().get(INDEX).getNomeEtiqueta());
        assertEquals(2L, resposta.getPerfil().getId());
        assertEquals("ADMIN", resposta.getPerfil().getNome());
    }



    private static void assertUsuarioPadrao(Usuario resposta) {
        assertNotNull(resposta);
        assertEquals(Usuario.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
        assertEquals(EMAIL, resposta.getEmail());
        assertEquals(Etiqueta.class, resposta.getEtiquetas().get(INDEX).getClass());
        assertEquals(ID, resposta.getEtiquetas().get(INDEX).getId());
        assertEquals(NOME_ETIQUETA, resposta.getEtiquetas().get(INDEX).getNomeEtiqueta());
    }



    private void startUser(){
        etiqueta = new Etiqueta(ID, NOME_ETIQUETA,null);
        perfil_usuario = new Perfil(1L,"USUARIO");
        perfil_admin = new Perfil(2L,"ADMIN");
        usuario = new Usuario(ID, EMAIL, SENHA, List.of(etiqueta),perfil_usuario);
        usuarioAtualizado = new Usuario(null,EMAIL,SENHA,List.of(etiqueta),perfil_usuario);
        admin = new Usuario(ID,EMAIL,SENHA,List.of(etiqueta),perfil_admin);
        op_usuario = Optional.of(new Usuario(ID,EMAIL,SENHA,List.of(etiqueta),perfil_usuario));
        op_admin = Optional.of(new Usuario(ID,EMAIL,SENHA,List.of(etiqueta),perfil_admin));
        usuarioPage = new PageImpl<>(List.of(usuario));
        adminPage = new PageImpl<>(List.of(admin));
        pageable=new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

    }

}
