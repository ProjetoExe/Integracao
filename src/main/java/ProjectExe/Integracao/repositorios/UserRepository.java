package ProjectExe.Integracao.repositorios;

import ProjectExe.Integracao.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    //Busca automaticamente os usuários por login
    UserDetails findByLogin(String login);
}
