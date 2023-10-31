package com.uam.projetoN1.repositories;

import com.uam.projetoN1.entities.Acesso;
import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    Optional<Acesso> findByUsuarioAndEtiqueta(Usuario usuario, Etiqueta etiqueta);

    List<Acesso> findAllByEtiqueta(Etiqueta etiqueta);

}
