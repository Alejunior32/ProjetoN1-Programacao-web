package com.uam.projetoN1.services;


import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Perfil;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.exceptions.EntityNotFoundException;
import com.uam.projetoN1.repositories.EtiquetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class EtiquetaServiceTest {

    public static final long ID = 1L;
    public static final String NOME_ETIQUETA = "corinthians";
    public static final int INDEX = 0;
    public static final String EMAIL = "gft@gft.com";
    public static final String SENHA = "1234";
    @Mock
    private EtiquetaRepository etiquetaRepository;

    @InjectMocks
    private EtiquetaService etiquetaService;

    private Etiqueta etiqueta;
    private Optional<Etiqueta> op_etiqueta;
    private Usuario usuario;
    private Optional<Usuario> op_usuario;
    private Perfil perfil;

    Page<Etiqueta> etiquetaPage;
    Pageable pageable;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        startEtiqueta();
    }

    private static void assertEtiqueta(Etiqueta resposta) {
        assertNotNull(resposta);
        assertEquals(Etiqueta.class, resposta.getClass());
        assertEquals(ID, resposta.getId());
        assertEquals(NOME_ETIQUETA, resposta.getNomeEtiqueta());
    }

    private void assertEntityNotFoundException() {
        try{
            etiquetaService.buscarEtiqueta(ID);
        }catch (RuntimeException exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
    }

    private static void assertListaEtiqueta(List<Etiqueta> resposta) {
        assertNotNull(resposta);
        assertEquals(1, resposta.size());
        assertEquals(Etiqueta.class, resposta.get(INDEX).getClass());

        assertEquals(ID, resposta.get(INDEX).getId());
        assertEquals(NOME_ETIQUETA, resposta.get(INDEX).getNomeEtiqueta());
    }

    private void assertPage(Page<Etiqueta> resposta) {
        assertEquals(1,resposta.getTotalElements());
        assertEquals(1,resposta.getTotalPages());
        assertEquals(Etiqueta.class,resposta.stream().findFirst().get().getClass());
        assertEquals(ID,resposta.stream().findFirst().get().getId());
        assertEquals(NOME_ETIQUETA,resposta.stream().findFirst().get().getNomeEtiqueta());
    }

    @Test
    public void deveBuscarEtiquetaPeloIdERetornarUmaEtiqueta() throws Exception{
        when(etiquetaRepository.findById(anyLong())).thenReturn(op_etiqueta);
        Etiqueta resposta = etiquetaService.buscarEtiqueta(ID);
        assertEtiqueta(resposta);
    }


    @Test
    public void deveBuscarEtiquetaPeloIdELancarAException() throws Exception{
        when(etiquetaRepository.findById(anyLong())).thenThrow(new EntityNotFoundException("Etiqueta não encontrada"));
        assertEntityNotFoundException();
    }

    @Test
    public void deveBuscarEtiquetaPeloNomeERetornarUmaEtiqueta() throws Exception{
        when(etiquetaRepository.findByNomeEtiqueta(anyString())).thenReturn(op_etiqueta);
        Etiqueta resposta = etiquetaService.buscarEtiquetaPeloNome(NOME_ETIQUETA);
        assertEtiqueta(resposta);
    }

    @Test
    public void deveBuscarEtiquetaPeloNomeELancarException(){
        when(etiquetaRepository.findByNomeEtiqueta(anyString())).thenThrow(new EntityNotFoundException("Etiqueta não encontrada"));
        assertEntityNotFoundException();
    }

    @Test
    public void deveBuscarEtiquetasDoUsuarioERetornarUmaLista() throws Exception{
        when(etiquetaRepository.findAllByUsuarios(any())).thenReturn(List.of(etiqueta));
        List<Etiqueta> resposta = etiquetaService.buscarEtiquetasDoUsuarioLista(usuario);
        assertListaEtiqueta(resposta);
    }

    @Test
    public void deveBuscarEtiquetasERetornarUmaLista() throws Exception{
        when(etiquetaRepository.findAll()).thenReturn(List.of(etiqueta));
        List<Etiqueta> resposta = etiquetaService.buscarTodasEtiquetasHistorico();
        assertListaEtiqueta(resposta);
    }

    @Test
    public void deveBuscarEtiquetasDoUsuarioERetornarUmaPage() throws Exception{
        when(etiquetaRepository.findAllByUsuarios(any(),any())).thenReturn(etiquetaPage);
        Page<Etiqueta> resposta = etiquetaService.buscarEtiquetasDoUsuario(usuario,pageable);
        assertPage(resposta);
    }

    @Test
    public void deveBuscarEtiquetasERetornarUmaPage() throws Exception{
        when(etiquetaRepository.findAll((Pageable) any())).thenReturn(etiquetaPage);
        Page<Etiqueta> resposta = etiquetaService.buscarTodasEtiquetas(pageable);
        assertPage(resposta);
    }

    @Test
    public void deveSalvarEtiquetaERetornarSucesso(){
        when(etiquetaRepository.save(any())).thenReturn(etiqueta);
        Etiqueta resposta = etiquetaService.salvarEtiqueta(etiqueta);
        assertEtiqueta(resposta);
    }

    @Test
    public void deveVerificarAExistenciaDeUmaEtiquetaNaoExistente(){
        when(etiquetaRepository.existsByNomeEtiqueta(anyString())).thenReturn(false);
        when(etiquetaRepository.save(any())).thenReturn(etiqueta);
        Etiqueta resposta = etiquetaService.verificarExistenciaEtiqueta(etiqueta);
        assertEtiqueta(resposta);
    }

    @Test
    public void deveVerificarAExistenciaDeUmaEtiquetaExistente(){
        when(etiquetaRepository.existsByNomeEtiqueta(anyString())).thenReturn(true);
        when(etiquetaRepository.findByNomeEtiqueta(anyString())).thenReturn(op_etiqueta);
        Etiqueta resposta = etiquetaService.verificarExistenciaEtiqueta(etiqueta);
        assertEtiqueta(resposta);
    }



    private void startEtiqueta(){
        etiqueta = new Etiqueta(ID, NOME_ETIQUETA,null);
        op_etiqueta = Optional.of(new Etiqueta(ID, NOME_ETIQUETA,null));
        perfil = new Perfil(ID,"USUARIO");
        usuario = new Usuario(ID, EMAIL, SENHA,List.of(etiqueta),perfil);
        etiquetaPage = new PageImpl<>(List.of(etiqueta));
        pageable = new Pageable() {
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
