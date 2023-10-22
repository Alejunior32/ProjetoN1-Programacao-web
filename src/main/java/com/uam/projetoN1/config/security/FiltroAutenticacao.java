package com.uam.projetoN1.config.security;

import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.services.AutenticacaoService;
import com.uam.projetoN1.services.UsuarioService;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class FiltroAutenticacao extends OncePerRequestFilter {


    private final AutenticacaoService autenticacaoService;


    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        if(header != null && header.startsWith("Bearer ")) {
            token = header.substring(7, header.length());
        }


        if(autenticacaoService.verificaToken(token)) {
            Long idUsuario = autenticacaoService.retornarIdUsuario(token);
            Usuario usuario = usuarioService.buscarUsuarioPeloID(idUsuario);
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }
}