package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.*;
import ProjectExe.Integracao.servicos.PromocaoServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/promocoes")
public class PromocaoControle {

    @Autowired
    private PromocaoServico promocaoServico;

    //busca promoções por ID detalhadamente (somente no botão editar)
    @GetMapping(value = "/{id}/detalhes")
    public ResponseEntity<PromocaoDTO> buscarPorId(@PathVariable Long id){
        PromocaoDTO resultado = promocaoServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos os registros com filtro de id ou data
    @GetMapping
    public ResponseEntity<Page<PromocaoResumidaDTO>> buscarTodos(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "minData", defaultValue = "") String minData,
            @RequestParam(value = "maxData", defaultValue = "") String maxData,
            Pageable pageable) {
        Page<PromocaoResumidaDTO> resultado = promocaoServico.buscarTodos(id, minData, maxData, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir uma promoção
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody PromocaoDTO obj){
        PromocaoDTO entidade = promocaoServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getPromocaoId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Promoção " + entidade.getPromocaoId() + " inserida com sucesso!"));
    }

    //excluir uma promoção
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        promocaoServico.deletar(id);
        return ResponseEntity.ok(MensagemDTO.of("Promoção " + id + " excluída com sucesso"));
    }
}