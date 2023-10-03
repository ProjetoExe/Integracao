package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.config.TokenServico;
import ProjectExe.Integracao.dto.AutenticacaoDTO;
import ProjectExe.Integracao.dto.RespostaLoginDTO;
import ProjectExe.Integracao.dto.UsuarioCadastroDTO;
import ProjectExe.Integracao.entidades.Usuario;
import ProjectExe.Integracao.repositorios.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoControle {

    @Autowired
    private AuthenticationManager gerenciadorAutenticacao;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenServico tokenServico;

    //Login com usuário com o método de autenticação
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dto){
        var loginPassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var autenticacao = this.gerenciadorAutenticacao.authenticate(loginPassword);
        var token = tokenServico.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new RespostaLoginDTO(token));
    }

    //Criação de novo Usuário
    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody @Valid UsuarioCadastroDTO dto){
        if (this.usuarioRepositorio.findByLogin(dto.getLogin()) != null) return ResponseEntity.badRequest().build();
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getPassword());
        Usuario novoUsuario = new Usuario(dto.getLogin(), senhaCriptografada, dto.getEmail(), dto.getPermissao());
        this.usuarioRepositorio.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
