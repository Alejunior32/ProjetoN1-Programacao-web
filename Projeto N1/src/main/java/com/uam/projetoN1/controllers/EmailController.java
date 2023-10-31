package com.uam.projetoN1.controllers;

import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.EmailSenderService;
import com.uam.projetoN1.services.EtiquetaService;
import com.uam.projetoN1.services.NoticiaService;
import com.uam.projetoN1.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/mail/enviar")
public class EmailController {

    private final NoticiaService noticiasService;

    private final EmailSenderService emailSenderService;

    private final UsuarioService usuarioService;

    private final EtiquetaService etiquetaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> enviarEmail(@RequestParam Long usuarioId){
        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(usuarioId);

        List<Etiqueta> etiquetas = etiquetaService.buscarEtiquetasDoUsuarioLista(usuario);
        List<String> nomeEtiquetas = new ArrayList<>();
        for (Etiqueta etiqueta : etiquetas){
            nomeEtiquetas.add(etiqueta.getNomeEtiqueta());
            etiquetaService.buscarEtiquetaDoUsuario(usuarioId,etiqueta);
        }

        emailSenderService.EnviarEmail(usuario.getEmail(), "Notícias diária",noticiasService.noticiasDataAtual(nomeEtiquetas).toString());
        return ResponseEntity.ok().build();
    }

}
