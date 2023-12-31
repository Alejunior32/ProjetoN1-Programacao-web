package com.uam.projetoN1.controllers;

import com.uam.projetoN1.services.AutenticacaoService;
import com.uam.projetoN1.dto.Autenticacao.AutenticacaoDTO;
import com.uam.projetoN1.dto.Autenticacao.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AutenticacaoController {

    @Autowired
    AutenticacaoService autenticacaoService;

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authDTO){

        try{
            return ResponseEntity.ok(autenticacaoService.autenticar(authDTO));
        }catch (AuthenticationException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

}
