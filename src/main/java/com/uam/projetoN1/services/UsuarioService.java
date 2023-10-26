package com.uam.projetoN1.services;

import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.exceptions.AdminNaoPodeTerEtiquetaException;
import com.uam.projetoN1.exceptions.EtiquetaJaExisteException;
import com.uam.projetoN1.exceptions.UsuarioComEmailJaExisteException;
import com.uam.projetoN1.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario){
        if (!usuarioRepository.existsByEmail(usuario.getEmail())){
            return usuarioRepository.save(usuario);
        }
        throw new UsuarioComEmailJaExisteException("Já Existe um usuário com esse email");
    }

    public Usuario buscarUsuarioPeloID(Long id){
        Optional<Usuario> op_usuario = usuarioRepository.findById(id);
        return op_usuario.orElseThrow(() -> new EntityNotFoundException("Não encontrado")  );
    }

    public Usuario buscarAdminPeloPerfil(Long id) {
        Optional<Usuario>  op_admin = usuarioRepository.findByIdAndPerfil_nome(id,"ADMIN");
        return op_admin.orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
    }

    public Usuario buscarUsuarioPeloPerfil(Long id){
        Optional<Usuario> op_usuario = usuarioRepository.findByIdAndPerfil_nome(id,"USUARIO");
        return op_usuario.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado") );
    }

    public Usuario buscarUsuarioPorEmail(String email){
        Optional<Usuario> op_usuario= usuarioRepository.findByEmail(email);
        return op_usuario.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    public Page<Usuario> buscarTodosUsuarios(Pageable pageable){
        return usuarioRepository.findAllByPerfil_nome(pageable,"USUARIO");
    }

    public Page<Usuario> buscarTodosAdmins(Pageable pageable) {
        return usuarioRepository.findAllByPerfil_nome(pageable,"ADMIN");
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = this.buscarUsuarioPeloID(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorEmail(username);
    }

    public Usuario atualizarUsuario(Usuario usuario,Long id){
        Usuario usuarioInicial = this.buscarUsuarioPeloID(id);
        usuario.setId(usuarioInicial.getId());
        return usuarioRepository.save(usuario);
    }

    public Usuario salvarEtiqueta(Long id, Etiqueta etiqueta){

        Usuario usuario = buscarUsuarioPeloID(id);
        List<Etiqueta> etiquetas = usuario.getEtiquetas();

        if(usuario.getPerfil().getNome().equals("USUARIO")){
            if (etiquetas.contains(etiqueta)){
                throw new EtiquetaJaExisteException("Etiqueta já existe para esse usuário");
            }else{
                etiquetas.add(etiqueta);
                return atualizarUsuario(usuario,id);
            }
        }

        throw new AdminNaoPodeTerEtiquetaException("Não é possivel adicionar Etiqueta para Admin");

    }
}
