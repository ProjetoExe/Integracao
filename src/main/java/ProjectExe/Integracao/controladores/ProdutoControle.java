package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.servicos.ProdutoServico;
import ProjectExe.Integracao.dto.MensagemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoControle {

    @Autowired
    private ProdutoServico produtoServico;

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
        ProdutoDTO resultado = produtoServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca produtos por id, nome, ativo, marca, preço inicial e final
    @GetMapping
    public ResponseEntity<Page<ProdutoResumidoDTO>> buscarTodosProdutos(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "ean", required = false) String ean,
            @RequestParam(value = "ativo", required = false) Integer opt_ativo,
            @RequestParam(value = "marcas", required = false) List<String> marcas,
            @RequestParam(value = "categorias", required = false) List<String> categorias,
            @RequestParam(value = "precoIni", defaultValue = "0.00") BigDecimal precoInicial,
            @RequestParam(value = "precoFim", defaultValue = "1000000.00") BigDecimal precoFinal, //1.000.000
            @RequestParam(value = "estIni", defaultValue = "0.00") Double estoqueInicial,
            @RequestParam(value = "estFim", defaultValue = "100000.00") Double estoqueFinal, //100.000
            Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = produtoServico.buscarTodosProdutos(id, nome, ean, opt_ativo, marcas, categorias, precoInicial, precoFinal, estoqueInicial, estoqueFinal, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //exportar produtos para excel
    @GetMapping(value = "/exportarexcel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> gerarPlanilhaExcel()  {
        byte[] planilhaBytes = produtoServico.exportarParaExcel();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=planilha.xlsx")
                .body(planilhaBytes);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<MensagemDTO> inserir(@Valid @RequestBody ProdutoDTO obj){
        ProdutoDTO entidade = produtoServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getProdutoId()).toUri();
        return ResponseEntity.created(uri).body(MensagemDTO.of("Produto " + entidade.getProdutoId() + " cadastrado com sucesso!"));
    }

    //atualizar dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto){
        ProdutoDTO entidade = produtoServico.atualizar(id, dto);
        return ResponseEntity.ok().body(MensagemDTO.of("Produto " + id + " atualizado com sucesso!"));
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id){
        produtoServico.deletar(id);
        return ResponseEntity.ok().body(MensagemDTO.of("Produto " + id + " EXCLUÍDO COM SUCESSO!"));
    }

    //remover tamanho de um produto pelo parâmetro tamanho
    @DeleteMapping(value = "/{id}/grade/{tamanho}")
    public ResponseEntity<MensagemDTO> removerGrade(@PathVariable Long id, @PathVariable String tamanho){
        produtoServico.removerGrade(id, tamanho);
        return ResponseEntity.ok().body(MensagemDTO.of("Tamanho " + tamanho + " de Produto " + id + " EXCLUÍDO COM SUCESSO!"));
    }
}