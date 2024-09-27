package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.*;
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

    @GetMapping(value = "/codigo/{clienteId}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long clienteId){
        ClienteDTO resultado = clienteServico.buscarPorId(clienteId);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos os registros com filtro de id, nome, email e cpf
    @GetMapping
    public ResponseEntity<Page<ClienteResumidoDTO>> buscarTodos(
            @RequestParam(value = "id", required = false) Long clienteId,
            @RequestParam(value = "nome", required = false) String nomeCli,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            Pageable pageable) {
        Page<ClienteResumidoDTO> resultado = clienteServico.buscarTodos(clienteId, nomeCli, email, cpf, cnpj, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody ClienteDTO obj){
        ClienteDTO entidade = clienteServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getClienteId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Cliente " + entidade.getClienteId()+ " cadastrado com sucesso!"));
    }

    //atualizar um registro
    @PutMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto){
        ClienteDTO entidade = clienteServico.atualizar(id, dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Cliente " + id + " atualizado com sucesso!"));
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        clienteServico.deletar(id);
        return ResponseEntity.ok(MensagemDTO.of("Cliente " + id + "excluída com sucesso"));
    }
}
