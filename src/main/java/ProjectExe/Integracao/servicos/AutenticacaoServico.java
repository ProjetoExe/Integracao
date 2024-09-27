package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.config.TokenServico;
import ProjectExe.Integracao.dto.AutenticacaoDTO;
import ProjectExe.Integracao.dto.UsuarioCadastroDTO;
import ProjectExe.Integracao.entidades.Usuario;
import ProjectExe.Integracao.entidades.enums.UsuarioPermissao;
import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRegistroExistente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AutenticacaoServico {

    @Autowired
    private AuthenticationManager gerenciadorAutenticacao;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenServico tokenServico;
    @Autowired
    private LogLoginServico logLoginServico;

    @PersistenceContext
    private EntityManager entityManager;

    //Login com usuário com o método de autenticação
    public String login(AutenticacaoDTO dto, HttpServletRequest request, HttpServletResponse response) {
        var loginPassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var autenticacao = this.gerenciadorAutenticacao.authenticate(loginPassword);

        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        logLoginServico.salvarLogLogin(usuario.getLogin(), usuario.getLoja().getLojaId(), usuario.getPermissao().getPermissao(), request.getRemoteAddr());

        if (usuario.getPermissao() == UsuarioPermissao.PROD) {
            return tokenServico.gerarToken(usuario);
        } else {
            configurarSessao(request, autenticacao);
            return "Autenticado com Sucesso!";
        }
    }

    private void configurarSessao(HttpServletRequest request, Authentication autenticacao) {
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
        request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = usuarioRepositorio.findByLogin(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome : " + username);
        }
        return userDetails;
    }

    //Criação de novo Usuário
    public void registro(UsuarioCadastroDTO dto) {
        if (this.usuarioRepositorio.findByLogin(dto.getLogin()) != null) {
            throw new ExcecaoRegistroExistente("Login informado já existe");
        } else if (this.usuarioRepositorio.buscarPorEmail(dto.getEmail()) != null) {
            throw new ExcecaoRegistroExistente("E-mail informado já cadastrado");
        } else {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getPassword());
            Usuario novoUsuario = new Usuario(dto.getNomeCompleto(), dto.getCpf(), dto.getLogin(), senhaCriptografada, dto.getEmail(), dto.getPermissao(), dto.getLoja());
            novoUsuario.setDataCadastro(Instant.now());
            this.usuarioRepositorio.save(novoUsuario);
        }
    }
}