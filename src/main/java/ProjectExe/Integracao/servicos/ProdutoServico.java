package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.Marca;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoImagemRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import ProjectExe.Integracao.servicos.enums.OperacaoImagem;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private ProdutoImagemRepositorio produtoImagemRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id){
        Optional<Produto> resultado = produtoRepositorio.findById(id);
        return resultado.map(ProdutoDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
    }

    //buscar todos os registros
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarTodos(Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarTodos(pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //buscar Produtos por Nome
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutoPorNome(String nome, Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarProdutoPorNome(nome, pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //buscar Produtos ativos
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> buscarProdutosAtivos(Pageable pageable){
        Page<Produto> resultado = produtoRepositorio.buscarProdutosAtivos(pageable);
        return resultado.map(ProdutoDTO::new);
    }

    //atualizar registro
    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO obj){
        try {
            Produto entidade = produtoRepositorio.getReferenceById(id);
            atualizarDadosProduto(entidade, obj);
            return new ProdutoDTO(produtoRepositorio.save(entidade));
        }catch (EntityNotFoundException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }
    }

    //atualizar imagens de um Produto
    @Transactional
    public ProdutoDTO atualizarImagem(Long id, ProdutoImagem produtoImagem, OperacaoImagem operacao) {
        Produto entidade = produtoRepositorio.findById(id).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
        if (operacao == OperacaoImagem.ADICIONAR) {
            entidade.addImagem(produtoImagem);
        } else if (operacao == OperacaoImagem.REMOVER) {
            entidade.removeImagem(produtoImagem);
        } else {
            throw new IllegalArgumentException("Operação inválida.");
        }
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //inserir novo registro
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj){
        Produto entidade = new Produto();
        atualizarDadosProduto(entidade, obj);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //excluir um registro
    //@Transactional //retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long id) {
        try {
            produtoRepositorio.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //Método utilizado no método de inserir e atualizar dados
    private void atualizarDadosProduto(Produto entidade, ProdutoDTO dto){
        Marca marca = marcaRepositorio.findById(dto.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada"));
        entidade.setNome(dto.getNome());
        entidade.setDescricaoCurta(dto.getDescricaoCurta());
        entidade.setDescricaoCompleta(dto.getDescricaoCompleta());
        if (entidade.getId() == null){
            entidade.setDataCadastro(Instant.now());
        } else {
            entidade.setDataAtualizacao(Instant.now());
        }
        entidade.setAtivo(dto.getAtivo());
        entidade.setMarca(marca);
    }
}
