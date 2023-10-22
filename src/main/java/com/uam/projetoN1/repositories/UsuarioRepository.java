package com.uam.projetoN1.repositories;

import com.uam.projetoN1.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    boolean existsByEmail(String email);

    Page<Usuario> findAllByPerfil_nome(Pageable pageable, String nome);

    Optional<Usuario> findByIdAndPerfil_nome(Long id, String nome);

    Optional<Usuario> findByEmail(String email);
}
