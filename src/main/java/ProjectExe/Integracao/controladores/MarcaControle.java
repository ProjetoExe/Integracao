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

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(@PathVariable Long id){
        MarcaDTO resultado = marcaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos registros
    @GetMapping
    public ResponseEntity<Page<MarcaDTO>> buscarTodos(Pageable pageable){
        Page<MarcaDTO> resultado = marcaServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar registros por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Page<MarcaDTO>> buscarPorNome(@PathVariable String nome, Pageable pageable){
        Page<MarcaDTO> resultado = marcaServico.buscarPorNome(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MarcaDTO> inserir(@Valid @RequestBody MarcaDTO obj){
        MarcaDTO entidade = marcaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getId()).toUri();
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
