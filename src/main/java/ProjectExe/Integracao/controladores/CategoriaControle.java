package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.servicos.CategoriaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaControle {

    @Autowired
    private CategoriaServico categoriaServico;

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> buscaPorId(@PathVariable Long id){
        Categoria resultado = categoriaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<Categoria>> buscaTodos() {
        List<Categoria> resultado = categoriaServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }
}
