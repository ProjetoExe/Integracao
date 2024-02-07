package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.CupomDTO;
import ProjectExe.Integracao.entidades.Cupom;
import ProjectExe.Integracao.servicos.CupomServico;
import ProjectExe.Integracao.servicos.utilitarios.Mensagem;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/cupons")
public class CupomControle {

    @Autowired
    private CupomServico cupomServico;

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<CupomDTO> buscarPorId(@PathVariable Long id){
        CupomDTO resultado = cupomServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos os registros
    @GetMapping
    public ResponseEntity<Page<CupomDTO>> buscarTodos(Pageable pageable){
        Page<CupomDTO> resultado = cupomServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<Mensagem> inserir(@Valid @RequestBody CupomDTO obj){
        CupomDTO entidade = cupomServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(entidade.getCupomId()).toUri();
        return ResponseEntity.created(uri).body(Mensagem.of("Cupom " + entidade.getCupomId() + " cadastrado com sucesso!"));
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<Mensagem> atualizar(@PathVariable Long id, @Valid @RequestBody CupomDTO obj){
        CupomDTO entidade = cupomServico.atualizar(id, obj);
        return ResponseEntity.ok().body(Mensagem.of("Cupom " + id + " atualizado com sucesso!"));
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        cupomServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
