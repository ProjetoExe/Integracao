package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.AutenticacaoDTO;
import ProjectExe.Integracao.dto.RespostaLoginDTO;
import ProjectExe.Integracao.dto.UsuarioCadastroDTO;
import ProjectExe.Integracao.servicos.AutenticacaoServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoControle {

    @Autowired
    private AutenticacaoServico autenticacaoServico;

    //Login com usuário com o método de autenticação
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dto) {
        String token = autenticacaoServico.login(dto);
        return ResponseEntity.ok(new RespostaLoginDTO(token));
    }

    //Criação de novo Usuário
    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody @Valid UsuarioCadastroDTO dto) {
        autenticacaoServico.registro(dto);
        return ResponseEntity.ok().build();
    }
}
