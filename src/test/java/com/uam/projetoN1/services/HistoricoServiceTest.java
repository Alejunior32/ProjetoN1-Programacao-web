package com.uam.projetoN1.services;

import com.uam.projetoN1.entities.*;
import com.uam.projetoN1.repositories.AcessoRepository;
import com.uam.projetoN1.repositories.HistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class HistoricoServiceTest {

    public static final long ID = 1L;
    public static final String NOME_ETIQUETA = "sp";
    public static final int INDEX = 0;
    @InjectMocks
    private HistoricoService historicoService;

    @Mock
    private EtiquetaService etiquetaService;
    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private AcessoRepository acessoRepository;

    private Historico historico;
    private Acesso acesso;
    private Optional<Acesso> op_acesso;
    private List<Historico> historicos;
    private Usuario usuario;
    private Etiqueta etiqueta;
    private List<Etiqueta>etiquetas;
    private Perfil perfil;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        start();
    }

    @Test
    public void deveChamarSalvarHistorico(){
        when(historicoRepository.save(any())).thenReturn(historico);
        Historico resposta = historicoService.salvarHistorico(usuario,etiqueta);
        assertHistorico(resposta);
    }

    @Test
    public void deveCharmarBuscarHistoricoPeloUsuarioERetornarUmaLista(){
        when(historicoRepository.findAllByUsuario_Id(anyLong())).thenReturn(historicos);
        List<Historico> resposta = historicoService.buscarHistoricoPeloUsuario(ID);
        assertHistoricoList(resposta);
    }

    @Test
    public void deveChamarSalvarAcessoComAcessoExistente(){
        when(acessoRepository.findByUsuarioAndEtiqueta(any(),any())).thenReturn(op_acesso);
        when(acessoRepository.save(any())).thenReturn(acesso);
        Acesso resposta = historicoService.salvarAcesso(usuario,etiqueta);
        assertAcesso(resposta);
    }

    @Test
    public void deveChamarSalvarAcessoComAcessoNovo(){
        when(acessoRepository.findByUsuarioAndEtiqueta(any(),any())).thenReturn(Optional.empty());
        when(acessoRepository.save(any())).thenReturn(new Acesso(ID,usuario,etiqueta,1));
        Acesso resposta = historicoService.salvarAcesso(usuario,etiqueta);
        assertAcesso(resposta);
    }

    private void assertAcesso(Acesso resposta) {
        assertEquals(ID,resposta.getId());
        assertEquals(1,resposta.getAcessos());
        assertEquals(Etiqueta.class,resposta.getEtiqueta().getClass());
        assertEquals(Usuario.class,resposta.getUsuario().getClass());
    }

    private void assertHistoricoList(List<Historico> resposta) {
        assertEquals(Usuario.class, resposta.get(INDEX).getUsuario().getClass());
        assertEquals(Etiqueta.class, resposta.get(INDEX).getEtiqueta().getClass());
        assertEquals("10/10/10",historico.getData());
        assertEquals(ID,historico.getId());
    }

    private void assertHistorico(Historico resposta) {
        assertEquals(Usuario.class,resposta.getUsuario().getClass());
        assertEquals(Etiqueta.class,resposta.getEtiqueta().getClass());
        assertEquals("10/10/10",historico.getData());
        assertEquals(ID,historico.getId());
    }

    private void start(){
        perfil=new Perfil();
        etiqueta = new Etiqueta(ID, NOME_ETIQUETA,null);
        etiquetas = List.of(etiqueta);
        usuario = new Usuario(ID,"gft@gft.com","1234",etiquetas,perfil);
        historico = new Historico(ID,usuario,etiqueta,"10/10/10");
        historicos = new ArrayList<>();
        historicos.add(historico);
        acesso = new Acesso(ID,usuario,etiqueta,0);
        op_acesso = Optional.of(acesso);
    }

}
