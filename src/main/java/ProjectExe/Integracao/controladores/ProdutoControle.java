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

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoControle {

    @Autowired
    private ProdutoServico produtoServico;

    //buscar por ID
    @GetMapping(value = "/codigo/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
        ProdutoDTO resultado = produtoServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //busca produtos por id, nome, ativo, marca, preço inicial e final
    @GetMapping
    public ResponseEntity<Page<ProdutoResumidoDTO>> buscarTodos(
            @RequestParam(value = "id", required = false) Long produtoId,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "ref", required = false) String ref,
            @RequestParam(value = "ean", required = false) Long ean,
            @RequestParam(value = "marca", required = false) String marca,
            @RequestParam(value = "ativo", required = false) Integer optAtivo,
            @RequestParam(value = "categorias", required = false) List<String> categorias,
            @RequestParam(value = "precoIni", defaultValue = "0.00") Double precoInicial,
            @RequestParam(value = "precoFim", defaultValue = "100000.00") Double precoFinal, //10.000.00
            @RequestParam(value = "estIni", defaultValue = "0") Integer estoqueInicial,
            @RequestParam(value = "estFim", defaultValue = "10000") Integer estoqueFinal, //10000
            Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = produtoServico.buscarTodos(produtoId, nome, ref, ean, marca, optAtivo, categorias, precoInicial, precoFinal, estoqueInicial, estoqueFinal, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //exportar produtos para excel
    @GetMapping(value = "/exportarexcel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> gerarPlanilhaExcel()  {
        byte[] planilhaBytes = produtoServico.exportarParaExcel();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=Produtos.xlsx")
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
        return ResponseEntity.ok().body(MensagemDTO.of("Produto " + id + " excluído com sucesso!"));
    }

    //remover tamanho de um produto pelo parâmetro tamanho
    @DeleteMapping(value = "/{id}/grade/{tamanho}")
    public ResponseEntity<MensagemDTO> removerGrade(@PathVariable Long id, @PathVariable String tamanho){
        produtoServico.removerGrade(id, tamanho);
        return ResponseEntity.ok().body(MensagemDTO.of("Tamanho " + tamanho + " de Produto " + id + " excluído com sucesso!"));
    }
}