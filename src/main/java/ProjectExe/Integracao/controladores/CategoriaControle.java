package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.dto.CategoriaProdutoDTO;
import ProjectExe.Integracao.servicos.CategoriaServico;
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

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id){
        CategoriaDTO resultado = categoriaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todas os registros
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> buscarTodos(Pageable pageable) {
        Page<CategoriaDTO> resultado = categoriaServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar registros por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Page<CategoriaDTO>> buscarPorNome(@PathVariable String nome, Pageable pageable){
        Page<CategoriaDTO> resultado = categoriaServico.buscarPorNome(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar por ID trazendo os Produtos da Categoria
    @GetMapping(value = "/{id}/produtos")
    public ResponseEntity<CategoriaProdutoDTO> buscarCategoriaProdutoPorId(@PathVariable Long id){
        CategoriaProdutoDTO resultado = categoriaServico.buscarCategoriaProdutoPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<CategoriaDTO> inserir(@Valid @RequestBody CategoriaDTO obj){
        CategoriaDTO entidade = categoriaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getCategoriaId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto){
        CategoriaDTO entidade = categoriaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        categoriaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}