package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.*;
import ProjectExe.Integracao.servicos.ClasseServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/classes")
public class ClasseControle {

    @Autowired
    private ClasseServico classeServico;

    @GetMapping(value = "/codigo/{id}")
    public ResponseEntity<ClasseDTO> buscarPorId(@PathVariable Long id){
        ClasseDTO resultado = classeServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos os registros com filtro de nome e variação
    @GetMapping
    public ResponseEntity<Page<ClasseResumidoDTO>> buscarTodos(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "variacao", required = false) String variacao,
            Pageable pageable) {
        Page<ClasseResumidoDTO> resultado = classeServico.buscarTodos(nome, variacao, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody ClasseDTO obj){
        ClasseDTO entidade = classeServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getClasseId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Classe " + entidade.getClasseId() + " cadastrada com sucesso!"));
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClasseDTO obj){
        ClasseDTO entidade = classeServico.atualizar(id, obj);
        return ResponseEntity.ok().body(MensagemDTO.of("Classe " + id + " atualizada com sucesso!"));
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        classeServico.deletar(id);
        return ResponseEntity.ok(MensagemDTO.of("Classe " + id + " excluída com sucesso"));
    }
}