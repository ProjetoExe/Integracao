package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.config.TokenServico;
import ProjectExe.Integracao.dto.AutenticacaoDTO;
import ProjectExe.Integracao.dto.UsuarioCadastroDTO;
import ProjectExe.Integracao.entidades.Usuario;
import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRegistroExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServico {

    @Autowired
    private AuthenticationManager gerenciadorAutenticacao;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenServico tokenServico;

    //Login com usuário com o método de autenticação
    public String login(AutenticacaoDTO dto) {
        var loginPassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var autenticacao = this.gerenciadorAutenticacao.authenticate(loginPassword);

        return tokenServico.gerarToken((Usuario) autenticacao.getPrincipal());
    }

    //Criação de novo Usuário
    public void registro(UsuarioCadastroDTO dto) {
        if (this.usuarioRepositorio.findByLogin(dto.getLogin()) != null) {
            throw new ExcecaoRegistroExistente("Login informado já existe");
        } else {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getPassword());
            Usuario novoUsuario = new Usuario(dto.getLogin(), senhaCriptografada, dto.getEmail(), dto.getPermissao(), dto.getLoja());
            this.usuarioRepositorio.save(novoUsuario);
        }
    }
}
