package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.dto.VendaInsereAtualizaDTO;
import ProjectExe.Integracao.dto.VendaResumidaDTO;
import ProjectExe.Integracao.servicos.VendaServico;
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
            @RequestParam(value = "nomeCliente", defaultValue = "") String nomeCliente,
            Pageable pageable) {
        Page<VendaResumidaDTO> resultado = vendaServico.buscarTodos_VendasPorIdEClienteEData(id, minData, maxData, nomeCliente, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //atualizar registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody VendaInsereAtualizaDTO dto){
        VendaInsereAtualizaDTO entidade = vendaServico.atualizar(id, dto);
        String msg = "Venda atualizada com sucesso";
        return ResponseEntity.ok().body(msg);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<String> inserir(@Valid @RequestBody VendaInsereAtualizaDTO obj){
        VendaInsereAtualizaDTO entidade = vendaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getVendaId()).toUri();
        String msg = "Venda inserida com sucesso";
        return ResponseEntity.created(uri).body(msg);
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        vendaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}