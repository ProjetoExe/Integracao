package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    //Busca automaticamente os usuários por login
    UserDetails findByLogin(String login);
}
