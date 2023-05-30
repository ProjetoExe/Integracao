package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id){
        Produto resultado = produtoRepositorio.findById(id).get();
        return resultado;
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<Produto> buscarTodos(){
        List<Produto> resultado = produtoRepositorio.findAll();
        return resultado;
    }
}
