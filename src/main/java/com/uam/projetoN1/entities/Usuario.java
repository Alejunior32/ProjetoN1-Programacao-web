package com.uam.projetoN1.entities;


import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="usuario_etiqueta",
            joinColumns={@JoinColumn(name="usuario_id")},
            inverseJoinColumns={@JoinColumn(name="etiqueta_id")})
    private List<Etiqueta> etiquetas;

    @OneToMany(mappedBy = "usuario")
    private List<Historico> historicos;

    @OneToOne
    private Perfil perfil;

    public Usuario(Long id, String email, String senha, List<Etiqueta> etiquetas, Perfil perfil) {
        this.id = id;
        this.email = email;
        this.senha = passwordEncoder().encode(senha);
        this.etiquetas = etiquetas;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.perfil);
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}