package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoInsereAtualizaDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.CategoriaRepositorio;
import ProjectExe.Integracao.repositorios.MarcaRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoUnico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long produtoId) {
        Optional<Produto> resultado = produtoRepositorio.findById(produtoId);
        return resultado.map(ProdutoDTO::new)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(produtoId));
    }

    //busca produtos por id, nome e ativo
    @Transactional(readOnly = true)
    public Page<ProdutoResumidoDTO> buscarTodos_ProdutosPorIdENomeEAtivo(Long produtoId, String nome, char ativo, Pageable pageable) {
        Page<Produto> resultado = new PageImpl<>(Collections.emptyList());
        if (produtoId != null) {
            Optional<Produto> produto = produtoRepositorio.findById(produtoId);
            if (produto.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(produto.get()), pageable, 1);
            }
        } else if (!nome.isEmpty()) {
            resultado = produtoRepositorio.buscarProdutoPorNomeEAtivo(nome, ativo, pageable);
        } else {
            resultado = produtoRepositorio.buscarProdutosAtivos(ativo, pageable);
        }
        return resultado.map(ProdutoResumidoDTO::new);
    }

    //atualizar registro
    @Transactional
    public ProdutoInsereAtualizaDTO atualizar(Long produtoId, ProdutoInsereAtualizaDTO obj) {
        try {
            Produto entidade = produtoRepositorio.getReferenceById(produtoId);
            atualizarDadosProduto(entidade, obj);
            return new ProdutoInsereAtualizaDTO(produtoRepositorio.save(entidade));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado(produtoId);
        }
    }

    //inserir novo registro
    @Transactional
    public ProdutoInsereAtualizaDTO inserir(ProdutoInsereAtualizaDTO obj) {
        Produto entidade = new Produto();
        atualizarDadosProduto(entidade, obj);
        entidade.setAtivo('N');
        return new ProdutoInsereAtualizaDTO(produtoRepositorio.save(entidade));
    }

    //excluir um registro
    //@Transactional retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long produtoId) {
        try {
            produtoRepositorio.deleteById(produtoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado(produtoId);
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //inserir imagem ao Produto (por String imgUrl)
    @Transactional
    public ProdutoDTO atualizarImagens(Long produtoId, List<ProdutoImagem> imagens) {
        Produto entidade = produtoRepositorio.findById(produtoId)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(produtoId));
        List<ProdutoImagem> imagensExistente = entidade.getImagens();
        imagensExistente.removeIf(imagemExistente -> !imagens.contains(imagemExistente));
        imagens.stream()
                .filter(produtoImagem -> !imagensExistente.contains(produtoImagem))
                .forEach(produtoImagem -> {
                    if (produtoImagem != null && !produtoImagem.getImgUrl().isEmpty()) {
                        entidade.getImagens().add(produtoImagem);
                        produtoImagem.setProduto(entidade);
                    }
                });
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //adicionar tamanho ao produto
    @Transactional
    public ProdutoDTO adicionarGrade(Long produtoId, ProdutoGrade produtoGrade) {
        Produto entidade = produtoRepositorio.findById(produtoId)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(produtoId));
        entidade.getGrade().add(produtoGrade);
        produtoGrade.setProduto(entidade);
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //remover tamanho do produto
    @Transactional
    public ProdutoDTO removerGrade(Long produtoId, String tamanho) {
        Produto entidade = produtoRepositorio.findById(produtoId)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado(produtoId));
        entidade.getGrade().removeIf(produtoGrade -> produtoGrade.getTamanho().equals(tamanho));
        return new ProdutoDTO(produtoRepositorio.save(entidade));
    }

    //Método utilizado no método de inserir e atualizar dados
    private void atualizarDadosProduto(Produto entidade, ProdutoInsereAtualizaDTO dto) {
        if (entidade.getProdutoId() == null) {
            entidade.setDataCadastro(Instant.now());
        } else {
            entidade.setDataAtualizacao(Instant.now());
        }
        entidade.setNome(dto.getNome());
        entidade.setDescricaoCurta(dto.getDescricaoCurta());
        entidade.setDescricaoCompleta(dto.getDescricaoCompleta());
        entidade.setReferencia(dto.getReferencia());

        Marca marca = atualizarOuCadastrarMarca(dto.getMarca());
        entidade.setMarca(marca);

        Set<Categoria> categorias = new HashSet<>(atualizarOuCadastrarCategorias(dto.getCategorias()));
        entidade.getCategorias().addAll(categorias);
    }

    //Verifica, atualiza e cadastra a Marca, se necessário
    private Marca atualizarOuCadastrarMarca(Marca dto) {
        return marcaRepositorio.buscarPorNome(dto.getNome())
                .orElseGet(() -> {
                    Marca novaMarca = new Marca();
                    novaMarca.setNome(dto.getNome());
                    return marcaRepositorio.save(novaMarca);
                });
    }

    //Verifica, atualiza e cadastra as Categorias, se necessário
    private Set<Categoria> atualizarOuCadastrarCategorias(List<Categoria> categoriasDTO) {
        if (categoriasDTO.isEmpty()) {
            throw new ExcecaoRecursoUnico("O produto precisa conter pelo menos 1 categoria");
        } else {
            Set<Categoria> categorias = new HashSet<>();
            for (Categoria categoriaDTO : categoriasDTO) {
                Categoria categoria = categoriaRepositorio.buscarPorNome(categoriaDTO.getNome())
                        .orElseGet(() -> {
                            Categoria novaCategoria = new Categoria();
                            novaCategoria.setNome(categoriaDTO.getNome());
                            return categoriaRepositorio.save(novaCategoria);
                        });
                categorias.add(categoria);
            }
            return categorias;
        }
    }
}
