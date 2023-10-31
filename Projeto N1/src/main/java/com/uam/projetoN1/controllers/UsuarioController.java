package com.uam.projetoN1.controllers;


import com.uam.projetoN1.dto.Usuario.ConsultaUsuarioDTO;
import com.uam.projetoN1.dto.Usuario.ConsultaUsuarioSemEtiquetasDTO;
import com.uam.projetoN1.dto.Usuario.RegistroUsuarioDTO;
import com.uam.projetoN1.dto.Usuario.UsuarioMapper;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.EmailSenderService;
import com.uam.projetoN1.services.EtiquetaService;
import com.uam.projetoN1.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final EtiquetaService etiquetaService;

    private final EmailSenderService emailSenderService;

    public UsuarioController(UsuarioService usuarioService, EtiquetaService etiquetaService,EmailSenderService emailSenderService) {
        this.usuarioService = usuarioService;
        this.etiquetaService = etiquetaService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaUsuarioSemEtiquetasDTO> salvarUsuario(@RequestBody RegistroUsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.salvarUsuario(UsuarioMapper.fromDTO(usuarioDTO));
        emailSenderService.EnviarEmail(usuario.getEmail(), "Usuário Cadastrado com sucesso!","Seja muito bem vindo em nossa API de notícias");
        return ResponseEntity.ok(UsuarioMapper.fromEntityToConsultaSemEtiquetaDTO(usuario));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaUsuarioDTO> buscarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(id);
        return ResponseEntity.ok(UsuarioMapper.fromEtityToConsultaDTO(usuario));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ConsultaUsuarioDTO>> buscarTodosUsuarios(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios(pageable)
                .map(UsuarioMapper::fromEtityToConsultaDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaUsuarioSemEtiquetasDTO> atualizarUmUsuario(@PathVariable Long id,@RequestBody RegistroUsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.atualizarUsuario(UsuarioMapper.fromDTO(usuarioDTO),id);
        return ResponseEntity.ok(UsuarioMapper.fromEntityToConsultaSemEtiquetaDTO(usuario));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaUsuarioDTO> deletarUsuario(@PathVariable Long id){
        try{
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException exception){
            return ResponseEntity.notFound().build();
        }
    }



}