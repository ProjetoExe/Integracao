package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.ProdutoGrade;
import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.servicos.ProdutoServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //buscar todos os produtos resumidos
    @GetMapping
    public ResponseEntity<Page<ProdutoResumidoDTO>> buscarTodosResumido(Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = produtoServico.buscarProdutosResumido(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
        ProdutoDTO resultado = produtoServico.buscarPorId(id);
        return ResponseEntity.ok().body(resultado);
    }

    //buscar Produtos por nome
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Page<ProdutoDTO>> buscarProdutoPorNome(@PathVariable String nome, Pageable pageable){
        Page<ProdutoDTO> resultado = produtoServico.buscarProdutoPorNome(nome, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //busca produtos ativos
    @GetMapping(value = "/ativos")
    public ResponseEntity<Page<ProdutoDTO>> buscarProdutosAtivos(Pageable pageable){
        Page<ProdutoDTO> resultado = produtoServico.buscarProdutosAtivos(pageable);
        return ResponseEntity.ok().body(resultado);
    }

    //atualizar dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto){
        ProdutoDTO entidade = produtoServico.atualizar(id, dto);
        return ResponseEntity.ok().body(entidade);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<ProdutoDTO> inserir(@Valid @RequestBody ProdutoDTO obj, Categoria categoria){
        ProdutoDTO entidade = produtoServico.inserir(obj, categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entidade.getId()).toUri();
        return ResponseEntity.created(uri).body(entidade);
    }

    //exclui um registro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtoServico.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //inserir imagem ao produto (por String imgUrl)
    @PostMapping(value = "/{id}/imagens")
    public ResponseEntity<ProdutoDTO> atualizarImagens(@PathVariable Long id, @RequestBody List<ProdutoImagem> produtoImagem){
        ProdutoDTO entidade = produtoServico.atualizarImagens(id, produtoImagem);
        return ResponseEntity.ok().body(entidade);
    }

    //adicionar ou remover categoria ao produto (por ID da Categoria)
    @PostMapping(value = "/{id}/categorias")
    public ResponseEntity<ProdutoDTO> atualizarCategorias(@PathVariable Long id, @RequestBody List<Categoria> categoria){
        ProdutoDTO entidade = produtoServico.atualizarCategorias(id, categoria);
        return ResponseEntity.ok().body(entidade);
    }

    //adicionar tamanho ao produto
    @PostMapping(value = "/{id}/grade")
    public ResponseEntity<ProdutoDTO> inserirGrade(@PathVariable Long id, @RequestBody ProdutoGrade produtoGrade){
        ProdutoDTO entidade = produtoServico.adicionarGrade(id, produtoGrade);
        return ResponseEntity.ok().body(entidade);
    }

    //remover tamanho de um produto pelo parâmetro tamanho
    @DeleteMapping(value = "/{id}/grade/{tamanho}")
    public ResponseEntity<ProdutoDTO> removerGrade(@PathVariable Long id, @PathVariable String tamanho){
        ProdutoDTO entidade = produtoServico.removerGrade(id, tamanho);
        return ResponseEntity.ok().body(entidade);
    }
}
