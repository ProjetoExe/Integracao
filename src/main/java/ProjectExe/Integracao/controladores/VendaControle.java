package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.VendaDTO;
import ProjectExe.Integracao.servicos.VendaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vendas")
public class VendaControle {

    @Autowired
    private VendaServico vendaServico;

    //busca por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id){
        VendaDTO resultado = vendaServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca todas os registros
    @GetMapping
    public ResponseEntity<Page<VendaDTO>> buscarTodos(
            //@PageableDefault(page = 0, size = 1, sort = "dataVenda", direction = Sort.Direction.ASC)
            @RequestParam(value = "minData", defaultValue = "") String minData,
            @RequestParam(value = "maxData", defaultValue = "") String maxData,
            Pageable pageable) {
        Page<VendaDTO> resultado = vendaServico.buscarVendasPorData(minData, maxData, pageable);
        return ResponseEntity.ok().body(resultado);
    }
}
