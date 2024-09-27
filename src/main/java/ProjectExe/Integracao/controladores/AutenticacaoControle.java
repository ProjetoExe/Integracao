package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.AutenticacaoDTO;
import ProjectExe.Integracao.dto.UsuarioCadastroDTO;
import ProjectExe.Integracao.servicos.AutenticacaoServico;
import ProjectExe.Integracao.dto.MensagemDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<MensagemDTO> login(@RequestBody @Valid AutenticacaoDTO dto, HttpServletRequest request, HttpServletResponse response) {
        String resultado = autenticacaoServico.login(dto, request, response);
        return ResponseEntity.ok(MensagemDTO.of(resultado));
    }

    //Criação de novo Usuário
    @PostMapping("/registro")
    public ResponseEntity<MensagemDTO> registro(@RequestBody @Valid UsuarioCadastroDTO dto) {
        autenticacaoServico.registro(dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Cadastro efetuado com sucesso!"));
    }
}