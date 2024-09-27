package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.AtualizacaoPrecoDTO;
import ProjectExe.Integracao.dto.MensagemDTO;
import ProjectExe.Integracao.servicos.PrecoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/alteracaoprecos")
public class PrecoControle {

    @Autowired
    PrecoServico precoServico;

    //atualizar preços
    @PostMapping
    public ResponseEntity<MensagemDTO> atualizarPrecos(@RequestBody AtualizacaoPrecoDTO dto){
        precoServico.atualizarPrecos(dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Preços alterados com sucesso!"));
    }
}