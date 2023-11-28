package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Noticia.ListaNoticiasDTO;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/noticias/usuario")
public class NoticiaController {

    private final NoticiaService noticiaService;

    private final EtiquetaService etiquetaService;

    private final UsuarioService usuarioService;

    private final HistoricoService historicoService;

    private final AutenticacaoService autenticacaoService;

    public NoticiaController(NoticiaService noticiasService, EtiquetaService etiquetaService, UsuarioService usuarioService,
                             HistoricoService historicoService, AutenticacaoService autenticacaoService) {
        this.noticiaService = noticiasService;
        this.etiquetaService = etiquetaService;
        this.usuarioService = usuarioService;
        this.historicoService = historicoService;
        this.autenticacaoService = autenticacaoService;
    }


    @GetMapping("/com-data/")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<ListaNoticiasDTO> mostrarNoticias(HttpServletRequest request, @RequestParam Long etiquetaId, @RequestParam String data){
        Long usuarioId = autenticacaoService.retornarIdUsuario(autenticacaoService.getToken(request));
        Etiqueta etiqueta = etiquetaService.buscarEtiqueta(etiquetaId);
        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(usuarioId);
        etiquetaService.buscarEtiquetaDoUsuario(usuarioId,etiqueta);
        historicoService.salvarAcesso(usuario,etiqueta);
        historicoService.salvarHistorico(usuario,etiqueta);
        ListaNoticiasDTO listaNoticiasDTO = noticiaService.noticiaComData(etiqueta.getNomeEtiqueta(),data);
        return ResponseEntity.ok(listaNoticiasDTO);
    }

    @GetMapping("/data-atual/")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<ListaNoticiasDTO> mostrarNoticiasDataAtual(HttpServletRequest request, @RequestParam Long etiquetaId){
        Long usuarioId = autenticacaoService.retornarIdUsuario(autenticacaoService.getToken(request));
        Etiqueta etiqueta = etiquetaService.buscarEtiqueta(etiquetaId);
        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(usuarioId);
        etiquetaService.buscarEtiquetaDoUsuario(usuarioId,etiqueta);
        historicoService.salvarAcesso(usuario,etiqueta);
        historicoService.salvarHistorico(usuario,etiqueta);
        ListaNoticiasDTO listaNoticiasDTO = noticiaService.noticiaDataAtual(etiqueta.getNomeEtiqueta());
        return ResponseEntity.ok(listaNoticiasDTO);
    }

    @GetMapping("/todas-as-noticias-do-usuario/com-data/")
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<ListaNoticiasDTO>> mostrarTodasAsNoticiasDataEspecifica(HttpServletRequest request, @RequestParam String data){

        Long usuarioId = autenticacaoService.retornarIdUsuario(autenticacaoService.getToken(request));

        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(usuarioId);

        List<Etiqueta> etiquetas = etiquetaService.buscarEtiquetasDoUsuarioLista(usuario);
        List<String> nomeEtiquetas = new ArrayList<>();
        for (Etiqueta etiqueta : etiquetas){
            nomeEtiquetas.add(etiqueta.getNomeEtiqueta());
            etiquetaService.buscarEtiquetaDoUsuario(usuarioId,etiqueta);
            historicoService.salvarAcesso(usuario,etiqueta);
            historicoService.salvarHistorico(usuario,etiqueta);
        }

        return ResponseEntity.ok(noticiaService.noticiasComData(nomeEtiquetas,data));
    }


    @GetMapping("/todas-as-noticias-do-usuario/data-atual/")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<ListaNoticiasDTO>> mostrarTodasAsNoticiasDataAtual(HttpServletRequest request){

        Long usuarioId = autenticacaoService.retornarIdUsuario(autenticacaoService.getToken(request));

        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(usuarioId);

        List<Etiqueta> etiquetas = etiquetaService.buscarEtiquetasDoUsuarioLista(usuario);
        List<String> nomeEtiquetas = new ArrayList<>();
        for (Etiqueta etiqueta : etiquetas){
            nomeEtiquetas.add(etiqueta.getNomeEtiqueta());
            etiquetaService.buscarEtiquetaDoUsuario(usuarioId,etiqueta);
            historicoService.salvarAcesso(usuario,etiqueta);
            historicoService.salvarHistorico(usuario,etiqueta);
        }

        return ResponseEntity.ok(noticiaService.noticiasDataAtual(nomeEtiquetas));
    }

}

