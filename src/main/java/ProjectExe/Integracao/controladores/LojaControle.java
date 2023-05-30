package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ClienteDTO;
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
    public ResponseEntity<LojaDTO> buscarPorId(@PathVariable Long id){
        LojaDTO resultado = lojaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<LojaDTO>> buscarTodos() {
        List<LojaDTO> resultado= lojaServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por Razão Social
    @GetMapping(value = "/razaoSocial/{razaoSocial}")
    public ResponseEntity<List<LojaDTO>> buscarPorNome(@PathVariable String razaoSocial) {
        List<LojaDTO> resultado = lojaServico.buscarPorRazaoSocial(razaoSocial);
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por CNPJ
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<List<LojaDTO>> buscarPorCpf(@PathVariable String cnpj) {
        List<LojaDTO> resultado = lojaServico.buscarPorCNPJ(cnpj);
        return ResponseEntity.ok().body(resultado);
    }

    //atualiza dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<LojaDTO> atualizar(@PathVariable Long id, @RequestBody LojaDTO dto){
        LojaDTO entidade = lojaServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //insere novo registro
    @PostMapping
    public ResponseEntity<LojaDTO> inserir(@Valid @RequestBody LojaDTO obj){
        LojaDTO newDto = lojaServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    //exclui registro por ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        lojaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
