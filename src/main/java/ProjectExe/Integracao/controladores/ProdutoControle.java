package ProjectExe.Integracao.controladores;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.servicos.ProdutoServico;
import ProjectExe.Integracao.servicos.enums.OperacaoImagem;
import jakarta.servlet.http.HttpServletRequest;
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

    //buscar todos os registros
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscarTodos(Pageable pageable) {
        Page<ProdutoDTO> resultado = produtoServico.buscarTodos(pageable);
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

    //atualizar imagens de um Produto
    @RequestMapping(value = "/{id}/imagens", method = {RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<ProdutoDTO> atualizarImagens(@PathVariable Long id, @RequestBody ProdutoImagem produtoImagem, HttpServletRequest request){
        String metodo = request.getMethod();
        ProdutoDTO entidade;
        if (metodo.equals("POST")) {
            entidade = produtoServico.atualizarImagem(id, produtoImagem, OperacaoImagem.ADICIONAR);
        }else if (metodo.equals("DELETE")){
            entidade = produtoServico.atualizarImagem(id, produtoImagem, OperacaoImagem.REMOVER);
        }else {
            throw new UnsupportedOperationException("Método HTTP não suportado: " + metodo);
        }
        return ResponseEntity.ok().body(entidade);
    }

    //inserir novo registro
    @PostMapping
    public ResponseEntity<ProdutoDTO> inserir(@Valid @RequestBody ProdutoDTO obj){
        ProdutoDTO entidade = produtoServico.inserir(obj);
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
}
