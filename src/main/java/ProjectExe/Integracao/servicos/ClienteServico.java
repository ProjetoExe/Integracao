package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
}
