package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    //Verifica os usuários para extrair e carregar o usuário
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepositorio.findByLogin(login);
    }
}
