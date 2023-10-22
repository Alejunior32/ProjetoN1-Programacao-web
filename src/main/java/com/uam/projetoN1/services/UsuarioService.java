package com.uam.projetoN1.services;

import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarUsuarioPeloID(Long id){
        Optional<Usuario> op_usuario = usuarioRepository.findById(id);
        return op_usuario.orElseThrow(() -> new EntityNotFoundException("Não encontrado")  );
    }

    public Usuario buscarUsuarioPorEmail(String email){
        Optional<Usuario> op_usuario= usuarioRepository.findByEmail(email);
        return op_usuario.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorEmail(username);
    }
}
