package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.entidades.Venda;
import ProjectExe.Integracao.repositorios.VendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendaServico {

    @Autowired
    private VendaRepositorio vendaRepositorio;

    //busca por ID
    @Transactional(readOnly = true)
    public Venda buscarPorId(Long id){
        Venda resultado = vendaRepositorio.findById(id).get();
        return resultado;
    }

    //busca todos os registros
    @Transactional(readOnly = true)
    public List<Venda> buscarTodos(){
        List<Venda> resultado = vendaRepositorio.findAll();
        return resultado;
    }
}
