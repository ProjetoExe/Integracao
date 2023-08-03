package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoImagemRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoUnico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return resultado.map(ProdutoDTO::new)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
    }

    //buscar todos os produtos resumidos
    @Transactional(readOnly = true)
    public Page<ProdutoResumidoDTO> buscarProdutosResumido(Pageable pageable){
        Page<ProdutoResumidoDTO> resultado = produtoRepositorio.buscarProdutosResumido(pageable);
        return resultado;
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

    //inserir novo registro
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj, Categoria categoria){
        Produto entidade = new Produto();
        atualizarDadosProduto(entidade, obj);
        entidade.setAtivo('N');
        Categoria categoriaExistente = categoriaRepositorio.findById(categoria.getId())
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(categoria.getId()));
        entidade.getCategorias().add(categoriaExistente);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //excluir um registro
    //@Transactional retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long id) {
        try {
            produtoRepositorio.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ExcecaoRecursoNaoEncontrado(id);
        }catch (DataIntegrityViolationException e){
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //inserir imagem ao Produto (por String imgUrl)
    @Transactional
    public ProdutoDTO atualizarImagens(Long id, List<ProdutoImagem> imagens) {
        Produto entidade = produtoRepositorio.findById(id)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
        List<ProdutoImagem> imagensExistente = entidade.getImagens();
        imagensExistente.removeIf(imagemExistente -> !imagens.contains(imagemExistente));
        imagens.stream()
                .filter(produtoImagem -> !imagensExistente.contains(produtoImagem))
                .forEach(produtoImagem -> {
                    if(produtoImagem != null && !produtoImagem.getImgUrl().isEmpty()) {
                        entidade.getImagens().add(produtoImagem);
                        produtoImagem.setProduto(entidade);
                    }
                });
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //adicionar ou remover categoria ao produto (por ID da Categoria)
    @Transactional
    public ProdutoDTO atualizarCategorias(Long id, List<Categoria> categorias) {
        Produto entidade = produtoRepositorio.findById(id)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
        Set<Categoria> categoriasExistente = entidade.getCategorias();
        if (categorias.isEmpty()){
            throw new ExcecaoRecursoUnico("O produto precisa conter pelo menos 1 categoria");
        }
        categoriasExistente.removeIf(catExistente -> !categorias.contains(catExistente));
        categorias.stream()
                .filter(categoria -> !categoriasExistente.contains(categoria))
                .forEach(categoriaExistente -> entidade.getCategorias().add(categoriaExistente));
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //adicionar tamanho ao produto
    @Transactional
    public ProdutoDTO adicionarGrade(Long id, ProdutoGrade produtoGrade){
        Produto entidade = produtoRepositorio.findById(id)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
        entidade.getGrade().add(produtoGrade);
        produtoGrade.setProduto(entidade);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //remover tamanho do produto
    @Transactional
    public ProdutoDTO removerGrade(Long id, String tamanho){
        Produto entidade = produtoRepositorio.findById(id)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(id));
        entidade.getGrade().removeIf(produtoGrade -> produtoGrade.getTamanho().equals(tamanho));
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //Método utilizado no método de inserir e atualizar dados, incluindo a Marca
    private void atualizarDadosProduto(Produto entidade, ProdutoDTO dto){
        Marca marca = marcaRepositorio.findById(dto.getMarca().getId())
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(dto.getMarca().getId() + " da Marca" ));
        entidade.setNome(dto.getNome());
        entidade.setDescricaoCurta(dto.getDescricaoCurta());
        entidade.setDescricaoCompleta(dto.getDescricaoCompleta());
        if (entidade.getId() == null){
            entidade.setDataCadastro(Instant.now());
        }else {
            entidade.setDataAtualizacao(Instant.now());
        }
        entidade.setMarca(marca);
    }
}
