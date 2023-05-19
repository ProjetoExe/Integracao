package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.servicos.LojaServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/lojas")
public class LojaControle {

    @Autowired
    private LojaServico lojaServico;

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<LojaDTO> findById(@PathVariable Long id){
        LojaDTO result = lojaServico.findById(id);
        return ResponseEntity.ok().body(result);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<LojaDTO>> findAll() {
        List<LojaDTO> result = lojaServico.findAll();
        return ResponseEntity.ok().body(result);
    }

    //insere novo registro
    @PostMapping
    public ResponseEntity<LojaDTO> insert(@Valid @RequestBody LojaDTO obj){
        LojaDTO newDto = lojaServico.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    //exclui registro por ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        lojaServico.delete(id);
        return ResponseEntity.noContent().build();
    }
}
