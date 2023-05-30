package ProjectExe.Integracao.servicos;

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
    public Categoria buscarPorId(Long id){
        Categoria resultado = categoriaRepositorio.findById(id).get();
        return resultado;
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<Categoria> buscarTodos(){
        List<Categoria> resultado = categoriaRepositorio.findAll();
        return resultado;
    }
}
