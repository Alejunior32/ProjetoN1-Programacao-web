package com.uam.projetoN1.services;

import com.uam.projetoN1.entities.Etiqueta;
import com.uam.projetoN1.entities.Usuario;
import com.uam.projetoN1.exceptions.EntityNotFoundException;
import com.uam.projetoN1.exceptions.UsuarioNaoPossuiEtiquetaException;
import com.uam.projetoN1.repositories.EtiquetaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    private final UsuarioService usuarioService;

    public EtiquetaService(EtiquetaRepository etiquetaRepository, @Lazy UsuarioService usuarioService) {
        this.etiquetaRepository = etiquetaRepository;
        this.usuarioService = usuarioService;
    }

    public Etiqueta salvarEtiqueta(Etiqueta etiqueta){
        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta buscarEtiquetaPeloNome(String nome) {

        Optional<Etiqueta> op_etiqueta = etiquetaRepository.findByNomeEtiqueta(nome);
        return op_etiqueta.orElseThrow(() -> new EntityNotFoundException("Etiqueta Não encontrada") );
    }

    public Etiqueta verificarExistenciaEtiqueta(Etiqueta etiqueta){

        if  (!etiquetaRepository.existsByNomeEtiqueta(etiqueta.getNomeEtiqueta())){
            return salvarEtiqueta(etiqueta);
        }else {
            return buscarEtiquetaPeloNome(etiqueta.getNomeEtiqueta());
        }
    }
    public Page<Etiqueta> buscarTodasEtiquetas(Pageable pageable){
        return etiquetaRepository.findAll(pageable);
    }

    public Etiqueta buscarEtiqueta(Long id){
        Optional<Etiqueta> op_etiqueta = etiquetaRepository.findById(id);
        return op_etiqueta.orElseThrow(() -> new EntityNotFoundException("Etiqueta Não encontrada") );
    }

    public Page<Etiqueta> buscarEtiquetasDoUsuario(Usuario usuario, Pageable pageable){
        return etiquetaRepository.findAllByUsuarios(usuario, pageable);
    }

    public Etiqueta buscarEtiquetaDoUsuario(Long id_usuario ,Etiqueta etiqueta) {

        Usuario usuario = usuarioService.buscarUsuarioPeloPerfil(id_usuario);
        List<Etiqueta> etiquetas =usuario.getEtiquetas();

        if (!etiquetas.contains(etiqueta)){
            throw new UsuarioNaoPossuiEtiquetaException("Etiqueta não existe para esse usuário, por favor cadastre a etiqueta");
        }else return buscarEtiquetaPeloNome(etiqueta.getNomeEtiqueta());

    }

    public List<Etiqueta> buscarTodasEtiquetasHistorico(){
        return etiquetaRepository.findAll();
    }

    public List<Etiqueta> buscarEtiquetasDoUsuarioLista(Usuario usuario){
        return etiquetaRepository.findAllByUsuarios(usuario);
    }
}
