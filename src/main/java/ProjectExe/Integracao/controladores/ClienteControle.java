package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ClienteDTO;
import ProjectExe.Integracao.servicos.ClienteServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<List<ClienteDTO>> buscarTodos() {
        List<ClienteDTO> resultado = clienteServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<ClienteDTO>> buscarPorNome(@PathVariable String nome) {
        List<ClienteDTO> resultado = clienteServico.buscarPorNome(nome);
        return ResponseEntity.ok().body(resultado);
    }

    //busca registros por cpf
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<List<ClienteDTO>> buscarPorCpf(@PathVariable String cpf) {
        List<ClienteDTO> resultado = clienteServico.buscarPorCpf(cpf);
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
