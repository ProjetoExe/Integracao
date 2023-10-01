package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.enums.UsuarioPermissao;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="usuarioId")
public class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    @Column(unique = true)
    private String login;
    private String password;
    private UsuarioPermissao permissao;

    @Column(unique = true)
    private String email;
    private char ativo;

    public Usuario(String login, String password, String email, UsuarioPermissao role){
        this.login = login;
        this.password = password;
        this.email = email;
        this.permissao = role;
    }

    //Se o usuário for ADMIN, contém permissões de ADMIN e USER, se for USER, contém apenas de USER
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.permissao == UsuarioPermissao.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() { return login; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}