package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.servicos.ProdutoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoControle {

    @Autowired
    private ProdutoServico produtoServico;

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> buscaPorId(@PathVariable Long id){
        Produto resultado = produtoServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<Produto>> buscaTodos() {
        List<Produto> resultado = produtoServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }
}
