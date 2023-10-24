package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoInsereAtualizaDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.servicos.ProdutoServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    //busca produtos por id, nome e ativo (padrão de produtos ativos)
    @GetMapping
    public ResponseEntity<Page<ProdutoResumidoDTO>> buscarTodos_ProdutosPorIdENomeEAtivo(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categoriaId", defaultValue = "-1") Long categoriaId,
            @RequestParam(value = "ativo", defaultValue = "S") char ativo,
            Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = produtoServico.buscarTodos_ProdutosPorIdENomeEAtivo(id, nome, categoriaId, ativo, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //atualizar dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoInsereAtualizaDTO dto){
        ProdutoInsereAtualizaDTO entidade = produtoServico.atualizar(id, dto);
        String msg = "Produto " + id + " atualizado com Sucesso";
        return ResponseEntity.ok().body(msg);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<String> inserir(@Valid @RequestBody ProdutoInsereAtualizaDTO obj){
        ProdutoInsereAtualizaDTO entidade = produtoServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getProdutoId()).toUri();
        String msg = "Produto " + entidade.getProdutoId() + " cadastrado com Sucesso";
        return ResponseEntity.created(uri).body(msg);
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtoServico.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //remover tamanho de um produto pelo parâmetro tamanho
    @DeleteMapping(value = "/{id}/grade/{tamanho}")
    public ResponseEntity<ProdutoDTO> removerGrade(@PathVariable Long id, @PathVariable String tamanho){
        ProdutoDTO entidade = produtoServico.removerGrade(id, tamanho);
        return ResponseEntity.ok().body(entidade);
    }
}
