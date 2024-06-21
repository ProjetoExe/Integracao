package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.dto.MensagemDTO;
import ProjectExe.Integracao.servicos.LojaServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/lojas")
public class LojaControle {

    @Autowired
    private LojaServico lojaServico;

    //buscar apenas a Loja conectada pois o cliente só terá acesso as informações de sua loja
    @GetMapping(value = "/codigo/{id}")
    public ResponseEntity<LojaDTO> buscarPorId(@PathVariable Long id){
        LojaDTO resultado = lojaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<LojaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LojaDTO dto){
        LojaDTO entidade = lojaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //inserir um registro
    @PostMapping
    public ResponseEntity<LojaDTO> inserir(@Valid @RequestBody LojaDTO obj){
        LojaDTO newDto = lojaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getLojaId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    //excluir registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        lojaServico.deletar(id);
        return ResponseEntity.ok(MensagemDTO.of("Loja " + id + "excluída com sucesso"));
    }
}
