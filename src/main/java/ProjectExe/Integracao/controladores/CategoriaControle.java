package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.servicos.CategoriaServico;
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
@RequestMapping(value = "/categorias")
public class CategoriaControle {

    @Autowired
    private CategoriaServico categoriaServico;

    //buscar todos os registros com filtro de nome
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> buscarTodos(
            @RequestParam(value = "nome", required = false) String nome,
            Pageable pageable) {
        Page<CategoriaDTO> resultado = categoriaServico.buscarTodos(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody CategoriaDTO obj){
        CategoriaDTO entidade = categoriaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getCategoriaId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Categoria " + entidade.getCategoriaId()+ " cadastrada com sucesso!"));
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto){
        CategoriaDTO entidade = categoriaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Categoria " + id + " atualizada com sucesso!"));
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        categoriaServico.deletar(id);
        return ResponseEntity.ok(MensagemDTO.of("Categoria " + id + "excluída com sucesso"));
    }
}