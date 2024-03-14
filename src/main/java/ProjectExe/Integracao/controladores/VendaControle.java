package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.dto.VendaInsereAtualizaDTO;
import ProjectExe.Integracao.dto.VendaResumidaDTO;
import ProjectExe.Integracao.servicos.VendaServico;
import ProjectExe.Integracao.dto.MensagemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/vendas")
public class VendaControle {

    @Autowired
    private VendaServico vendaServico;

    //busca vendas por ID detalhadamente
    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id){
        VendaDTO resultado = vendaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos os registros com filtro de id, data e cliente
    @GetMapping
    public ResponseEntity<Page<VendaResumidaDTO>> buscarTodos_VendasPorData(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "minData", defaultValue = "") String minData,
            @RequestParam(value = "maxData", defaultValue = "") String maxData,
            Pageable pageable) {
        Page<VendaResumidaDTO> resultado = vendaServico.buscarTodos_VendasPorIdEClienteEData(id, minData, maxData, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody VendaInsereAtualizaDTO obj){
        VendaInsereAtualizaDTO entidade = vendaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getVendaId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Venda " + entidade.getVendaId() + " inserida com sucesso!"));
    }

    //atualizar registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VendaInsereAtualizaDTO dto){
        VendaInsereAtualizaDTO entidade = vendaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Venda " + id + "atualizada com sucesso!"));
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        vendaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}