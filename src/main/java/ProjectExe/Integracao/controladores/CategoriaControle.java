package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.dto.CategoriaProdutoDTO;
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
    public ResponseEntity<CategoriaDTO> buscaPorId(@PathVariable Long id){
        CategoriaDTO resultado = categoriaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> buscaTodos() {
        List<CategoriaDTO> resultado = categoriaServico.buscarTodos();
        return ResponseEntity.ok().body(resultado);
    }

    //busca por ID trazendo os Produtos da Categoria
    @GetMapping(value = "/{id}/produtos")
    public ResponseEntity<CategoriaProdutoDTO> buscarCategoriaProdutoPorId(@PathVariable Long id){
        CategoriaProdutoDTO resultado = categoriaServico.buscarCategoriaProdutoPorId(id);
        return ResponseEntity.ok().body(resultado);
    }
}
