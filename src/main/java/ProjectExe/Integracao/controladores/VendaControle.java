package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.LojaDTO;
import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.servicos.LojaServico;
import ProjectExe.Integracao.servicos.VendaServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/vendas")
public class VendaControle {

    @Autowired
    private VendaServico vendaServico;

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Venda> buscaPorId(@PathVariable Long id){
        Venda resultado = vendaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<Venda>> buscaTodos() {
        List<Venda> resultado = vendaServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }
}
