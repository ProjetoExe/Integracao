package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.entidades.Loja;
import ProjectExe.Integracao.servicos.LojaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LojaControle {

    @Autowired
    private LojaServico lojaServico;

    @GetMapping
    public List<Loja> findAll() {
        List<Loja> result = lojaServico.findAll();
        return result;
    }
}
