package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.MarcaDTO;
import ProjectExe.Integracao.servicos.MarcaServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/marcas")
public class MarcaControle {

    @Autowired
    private MarcaServico marcaServico;

    //buscar todos os registros com filtro de id e nome
    @GetMapping
    public ResponseEntity<Page<MarcaDTO>> buscarTodos_PorIdNome(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            Pageable pageable){
        Page<MarcaDTO> resultado = marcaServico.buscarTodos_PorIdNome(id, nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MarcaDTO> inserir(@Valid @RequestBody MarcaDTO obj){
        MarcaDTO entidade = marcaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getMarcaId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<MarcaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MarcaDTO obj){
        MarcaDTO entidade = marcaServico.atualizar(id, obj);
        return ResponseEntity.ok().body(entidade);
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        marcaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
