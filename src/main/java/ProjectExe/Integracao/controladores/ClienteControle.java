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

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id){
        ClienteDTO resultado = clienteServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> buscarTodos(Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Page<ClienteDTO>> buscarPorNome(@PathVariable String nome, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarPorNome(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por cpf
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Page<ClienteDTO>> buscarPorCpf(@PathVariable String cpf, Pageable pageable) {
        Page<ClienteDTO> resultado = clienteServico.buscarPorCpf(cpf, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //atualiza dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO dto){
        ClienteDTO entidade = clienteServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //insere novo registro
    @PostMapping
    public ResponseEntity<ClienteDTO> inserir(@Valid @RequestBody ClienteDTO obj){
        ClienteDTO entidade = clienteServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }
}
