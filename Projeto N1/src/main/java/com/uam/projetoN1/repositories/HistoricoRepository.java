package com.uam.projetoN1.repositories;

import com.uam.projetoN1.entities.Historico;
import com.uam.projetoN1.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico,Long> {
    List<Historico> findAllByUsuario(Usuario usuario);

    List<Historico> findAllByUsuario_Id(Long id);
}
