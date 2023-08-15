package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.servicos.ClienteServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteControle {

    @Autowired
    private ClienteServico clienteServico;

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id){
        ClienteDTO resultado = clienteServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todas os registros
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> buscarTodos(Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar registros por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Page<ClienteDTO>> buscarPorNome(@PathVariable String nome, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarPorNome(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar registros por cpf
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Page<ClienteDTO>> buscarPorCpf(@PathVariable String cpf, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarPorCpf(cpf, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<ClienteDTO> inserir(@Valid @RequestBody ClienteDTO obj){
        ClienteDTO entidade = clienteServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getClienteId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto){
        ClienteDTO entidade = clienteServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //excluir um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        clienteServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
