package com.uam.projetoN1.repositories;

import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta,Long> {
    Optional<Etiqueta> findByNomeEtiqueta(String nome);

    Page<Etiqueta> findAllByUsuarios(Usuario usuario, Pageable pageable);

    List<Etiqueta> findAllByUsuarios(Usuario usuario);

    boolean existsByNomeEtiqueta(String nome);
}