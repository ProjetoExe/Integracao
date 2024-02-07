package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.servicos.CategoriaServico;
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
@RequestMapping(value = "/categorias")
public class CategoriaControle {

    @Autowired
    private CategoriaServico categoriaServico;

    //buscar todos os registros com filtro de id e nome
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> buscarTodos(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            Pageable pageable) {
        Page<CategoriaDTO> resultado = categoriaServico.buscarTodos_PorIdNome(id, nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<Mensagem> inserir(@Valid @RequestBody CategoriaDTO obj){
        CategoriaDTO entidade = categoriaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getCategoriaId()).toUri();
        return ResponseEntity.created(uri).body(Mensagem.of("Categoria " + entidade.getCategoriaId()+ " cadastrada com sucesso!"));
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<Mensagem> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto){
        CategoriaDTO entidade = categoriaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(Mensagem.of("Categoria " + id + " atualizada com sucesso!"));
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        categoriaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}