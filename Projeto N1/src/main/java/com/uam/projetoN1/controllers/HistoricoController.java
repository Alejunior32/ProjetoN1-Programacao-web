package com.uam.projetoN1.controllers;

import com.uam.projetoN1.dto.Acesso.ConsultaAcessosDTO;
import com.uam.projetoN1.dto.Historico.ConsultaHistoricoDTO;
import com.uam.projetoN1.dto.Historico.HistoricoMapper;
import com.uam.projetoN1.services.AutenticacaoService;
import com.uam.projetoN1.services.HistoricoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/historicos")
public class HistoricoController {

    private final HistoricoService historicoService;

    private final AutenticacaoService autenticacaoService;

    public HistoricoController(HistoricoService historicoService, AutenticacaoService autenticacaoService) {
        this.historicoService = historicoService;
        this.autenticacaoService = autenticacaoService;
    }


    @GetMapping("/usuario")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<List<ConsultaHistoricoDTO>> buscarHistoricoUsuario(HttpServletRequest request){
        return ResponseEntity.ok(historicoService.buscarHistoricoPeloUsuario(
                        autenticacaoService.retornarIdUsuario(autenticacaoService.getToken(request)))
                .stream().map(HistoricoMapper::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/admin/dez-maiores-acessados")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ConsultaAcessosDTO>> buscarEtiquetasMaisAcessadas(){
        return ResponseEntity.ok(historicoService.historicoEtiquetaMaisAcessado());
    }
}

