package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.CategoriaDTO;
import ProjectExe.Integracao.dto.CategoriaProdutoDTO;
import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServico {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long id){
        Categoria resultado = categoriaRepositorio.findById(id).get();
        return new CategoriaDTO(resultado);
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<CategoriaDTO> buscarTodos(){
        List<Categoria> resultado = categoriaRepositorio.findAll();
        return resultado.stream().map(x -> new CategoriaDTO(x)).toList();
    }

    //busca por ID trazendo os Produtos da Categoria
    @Transactional(readOnly = true)
    public CategoriaProdutoDTO buscarCategoriaProdutoPorId(Long id){
        Categoria resultado = categoriaRepositorio.findById(id).get();
        return new CategoriaProdutoDTO(resultado);
    }
}
