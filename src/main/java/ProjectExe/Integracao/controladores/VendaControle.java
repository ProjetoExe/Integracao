package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.VendaClienteDTO;
import ProjectExe.Integracao.dto.VendaDTO;
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

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id){
        VendaDTO resultado = vendaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar Vendas por data
    @GetMapping
    public ResponseEntity<Page<VendaResumidaDTO>> buscarTodos_VendasPorData(
            @RequestParam(value = "minData", defaultValue = "") String minData,
            @RequestParam(value = "maxData", defaultValue = "") String maxData,
            Pageable pageable) {
        Page<VendaResumidaDTO> resultado = vendaServico.buscarTodos_VendasPorData(minData, maxData, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar registros por Cliente
    @GetMapping(value = "/cliente/{cliente_id}")
    public ResponseEntity<Page<VendaClienteDTO>> buscarVendasPorCliente(@PathVariable Long cliente_id, Pageable pageable){
        Page<VendaClienteDTO> resultado = vendaServico.buscarVendasPorCliente(cliente_id, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //atualizar registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<VendaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VendaDTO dto){
        VendaDTO entidade = vendaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<VendaDTO> inserir(@Valid @RequestBody VendaDTO obj){
        VendaDTO entidade = vendaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        vendaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}