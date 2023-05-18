package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.repositorios.LojaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class LojaServico {

    @Autowired
    private LojaRepositorio lojaRepositorio;

    @Transactional(readOnly = true)
    public List<Loja> findAll(){
        List<Loja> result = lojaRepositorio.findAll();
        return result;
    }
}
