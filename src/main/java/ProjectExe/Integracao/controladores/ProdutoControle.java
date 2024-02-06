package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.servicos.ProdutoServico;
import ProjectExe.Integracao.servicos.utilitarios.Mensagem;
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

    //busca produtos por id, nome, categoria e ativo (padrão de produtos ativos)
    @GetMapping
    public ResponseEntity<Page<ProdutoResumidoDTO>> buscarTodos_ProdutosPorIdENomeEAtivo(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categoriaId", defaultValue = "-1") Long categoriaId,
            @RequestParam(value = "ativo", defaultValue = "S") char ativo,
            Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = produtoServico.buscarTodos_PorIdNomeCategoriaAtivo(id, nome, categoriaId, ativo, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<Mensagem> inserir(@Valid @RequestBody ProdutoDTO obj){
        ProdutoDTO entidade = produtoServico.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getProdutoId()).toUri();
        return ResponseEntity.created(uri).body(Mensagem.of("Produto " + entidade.getProdutoId() + " cadastrado com sucesso!"));
    }

    //atualizar dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<Mensagem> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto){
        ProdutoDTO entidade = produtoServico.atualizar(id, dto);
        return ResponseEntity.ok().body(Mensagem.of("Produto " + id + " atualizado com sucesso!"));
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