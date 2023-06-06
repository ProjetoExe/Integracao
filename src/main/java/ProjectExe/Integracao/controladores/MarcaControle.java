package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.MarcaDTO;
import ProjectExe.Integracao.servicos.MarcaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/marcas")
public class MarcaControle {

    @Autowired
    private MarcaServico marcaServico;

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(@PathVariable Long id){
        MarcaDTO resultado = marcaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar todos registros
    @GetMapping
    public ResponseEntity<Page<MarcaDTO>> buscarTodos(Pageable pageable){
        Page<MarcaDTO> resultado = marcaServico.buscarTodos(pageable);
        return ResponseEntity.ok().body(resultado);
    }
}
