package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Administrador.AdminMapper;
import com.uam.projetoN1.dto.Administrador.ConsultaAdminDTO;
import com.uam.projetoN1.dto.Administrador.RegistroAdminDTO;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.AutenticacaoService;
import com.uam.projetoN1.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/v1/admins")
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    private final UsuarioService adminService;

    private final AutenticacaoService autenticacaoService;

    public AdminController(UsuarioService adminService, AutenticacaoService autenticacaoService) {
        this.adminService = adminService;
        this.autenticacaoService = autenticacaoService;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ConsultaAdminDTO>> buscarTodosAdmins(@PageableDefault Pageable pageable){

        String header = request.getHeader("Authorization");

//        String header = authorization.toString();
        String token = header.substring(7,header.length());
        Usuario usuario = adminService.buscarUsuarioPeloID(autenticacaoService.retornarIdUsuario(token));

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return  ResponseEntity.ok(adminService.buscarTodosAdmins(pageable).map(AdminMapper::fromEntity));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaAdminDTO> salvarAdmin(@RequestBody RegistroAdminDTO adminDTO){
        Usuario administrador = adminService.salvarUsuario(AdminMapper.fromDTO(adminDTO));
        return ResponseEntity.ok(AdminMapper.fromEntity(administrador));
    }

    @GetMapping("{AdminId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaAdminDTO> buscarAdmin(@PathVariable Long AdminId){
        Usuario admin = adminService.buscarAdminPeloPerfil(AdminId);
        return ResponseEntity.ok(AdminMapper.fromEntity(admin));
    }

    @PutMapping("{AdminId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaAdminDTO> atualizarAdmin(@PathVariable Long AdminId,@RequestBody RegistroAdminDTO adminDTO){
        Usuario admin = adminService.atualizarUsuario(AdminMapper.fromDTO(adminDTO),AdminId);
        return ResponseEntity.ok(AdminMapper.fromEntity(admin));
    }

    @DeleteMapping("{AdminId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ConsultaAdminDTO> deletarAdmin(@PathVariable Long AdminId){
        adminService.deletarUsuario(AdminId);
        return ResponseEntity.ok().build();
    }

}
