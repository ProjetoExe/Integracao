package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoInsereAtualizaDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.*;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private ClasseRepositorio classeRepositorio;
    @Autowired
    private ClasseGradeRepositorio classeGradeRepositorio;
    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private ProdutoGradeRepositorio produtoGradeRepositorio;
    @Autowired
    private ProdutoImagemRepositorio produtoImagemRepositorio;

    //buscar por ID
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long produtoId) {
        Optional<Produto> resultado = produtoRepositorio.findById(produtoId);
        return resultado.map(ProdutoDTO::new)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + " não encontrado"));
    }

    //buscar todos os registros com filtro de id, nome, categoria e ativo
    @Transactional(readOnly = true)
    @Cacheable("produtos")
    public Page<ProdutoResumidoDTO> buscarTodos_PorIdNomeCategoriaAtivo(Long produtoId, String nome, Long categoriaId, char ativo, Pageable pageable) {
        Page<Produto> resultado = Page.empty();
        if (produtoId != null) {
            Optional<Produto> produto = produtoRepositorio.findById(produtoId);
            if (produto.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(produto.get()), pageable, 1);
            }
        } else if (!nome.isEmpty()) {
            resultado = produtoRepositorio.findByNomeContainingAndAtivo(nome, ativo, pageable);
        } else if (categoriaId != -1) {
            resultado = produtoRepositorio.findByCategoria(categoriaId, pageable);
        } else {
            resultado = produtoRepositorio.findByAtivo(ativo, pageable);
        }
        return resultado.map(ProdutoResumidoDTO::new);
    }

    //inserir novo registro
    @Transactional
    public ProdutoInsereAtualizaDTO inserir(ProdutoInsereAtualizaDTO obj) {
        Produto entidade = new Produto();
        atualizarDadosProduto(entidade, obj);
        entidade.setAtivo('N');
        return new ProdutoInsereAtualizaDTO(produtoRepositorio.save(entidade));
    }

    //atualizar registro
    @Transactional
    public ProdutoInsereAtualizaDTO atualizar(Long produtoId, ProdutoInsereAtualizaDTO obj) {
        try {
            Produto entidade = produtoRepositorio.getReferenceById(produtoId);
            atualizarDadosProduto(entidade, obj);
            return new ProdutoInsereAtualizaDTO(produtoRepositorio.save(entidade));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + " não encontrado");
        }
    }

    //excluir um registro
    //@Transactional retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    public void deletar(Long produtoId) {
        try {
            produtoRepositorio.deleteById(produtoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + " não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //remover tamanho do produto
    @Transactional
    public ProdutoDTO removerGrade(Long produtoId, String tamanho) {
        Produto entidade = produtoRepositorio.findById(produtoId)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + "  encontrado"));
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
        entidade.setReferencia(dto.getReferencia());
        entidade.setDescricaoCurta(dto.getDescricaoCurta());
        entidade.setDescricao(dto.getDescricao());
        entidade.setEstoqueTotal(dto.getEstoqueTotal());
        entidade.setPreco(dto.getPreco());
        entidade.setPrecoPromocional(dto.getPrecoPromocional());
        entidade.setTempoGarantia(dto.getTempoGarantia());
        entidade.setMensagemGarantia(dto.getMensagemGarantia());
        entidade.setComprimento(dto.getComprimento());
        entidade.setLargura(dto.getLargura());
        entidade.setAltura(dto.getAltura());
        entidade.setPeso(dto.getPeso());

        Classe classe = atualizarClasse(dto.getClasse());
        entidade.setClasse(classe);

        Marca marca = atualizarOuCadastrarMarca(dto.getMarca());
        entidade.setMarca(marca);

        Set<Categoria> categorias = new HashSet<>(atualizarOuCadastrarCategorias(dto.getCategorias()));
        entidade.getCategorias().clear();
        entidade.getCategorias().addAll(categorias);

        produtoRepositorio.save(entidade);
        List<ProdutoGrade> grades = new ArrayList<>(atualizarOuInserirGrade(entidade, dto.getGrade()));
        entidade.getGrade().addAll(grades);

        List<ProdutoImagem> imagems = new ArrayList<>(atualizarOuInserirImagens(entidade, dto.getImagens()));
        entidade.getImagens().addAll(imagems);
    }

    //Verifica se a classe existe para atualizar no produto
    private Classe atualizarClasse(Classe classeDTO) {
        return classeRepositorio.findById(classeDTO.getClasseId())
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Classe " + classeDTO.getClasseId() + " não existe"));
    }

    //Verifica, atualiza e cadastra a Marca, se necessário
    private Marca atualizarOuCadastrarMarca(Marca marcaDTO) {
        return marcaRepositorio.findByNome(marcaDTO.getNome())
                .orElseGet(() -> {
                    Marca novaMarca = new Marca();
                    novaMarca.setNome(marcaDTO.getNome());
                    return marcaRepositorio.save(novaMarca);
                });
    }

    //Verifica, atualiza e cadastra as Categorias, se necessário
    //Não está retornando no JSON ainda as categorias ao incluir, listagem sendo exibida vazia
    private Set<Categoria> atualizarOuCadastrarCategorias(List<Categoria> categoriasDTO) {
        Set<Categoria> categorias = new HashSet<>();
        for (Categoria categoriaDTO : categoriasDTO) {
            Categoria categoria = categoriaRepositorio.findByNome(categoriaDTO.getNome())
                    .orElseGet(() -> {
                        Categoria novaCategoria = new Categoria();
                        novaCategoria.setNome(categoriaDTO.getNome());
                        return categoriaRepositorio.save(novaCategoria);
                    });
            categorias.add(categoria);
        }
        return categorias;
    }

    //Atualiza e insere novos tamanhos a grade produtos se necessário
    private List<ProdutoGrade> atualizarOuInserirGrade(Produto produto, List<ProdutoGrade> gradesDTO) {
        return gradesDTO.stream()
                .map(grade -> {
                    Optional<ProdutoGrade> produtoGrade = produtoGradeRepositorio.buscarPorProdutoIdETamanho(produto.getProdutoId(), grade.getTamanho());
                    return produtoGrade.map(produtoExistente -> {
                        produtoExistente.setPreco(grade.getPreco());
                        produtoExistente.setPrecoPromocional(grade.getPrecoPromocional());
                        produtoExistente.setEan(grade.getEan());
                        produtoExistente.setQuantidadeEstoque(grade.getQuantidadeEstoque());
                        return produtoGradeRepositorio.save(produtoExistente);
                    }).orElseGet(() -> {
                        Optional<ClasseGrade> classeGrade = classeGradeRepositorio.buscarPorClasseETamanho(produto.getClasse().getClasseId(), grade.getTamanho());
                        return classeGrade.map(classe -> {
                            ProdutoGrade novaGrade = new ProdutoGrade();
                            novaGrade.setProduto(produto);
                            novaGrade.setTamanho(grade.getTamanho());
                            novaGrade.setPreco(grade.getPreco());
                            novaGrade.setPrecoPromocional(grade.getPrecoPromocional());
                            novaGrade.setEan(grade.getEan());
                            novaGrade.setQuantidadeEstoque(grade.getQuantidadeEstoque());
                            return produtoGradeRepositorio.save(novaGrade);
                        }).orElseGet(null); // Retorna null se o tamanho não existir na ClasseGrade
                    });
                })
                .filter(Objects::nonNull) // Filtra grades não nulas
                .collect(Collectors.toList());
    }

    //atualiza e insere novas imagens ao produto
    public List<ProdutoImagem> atualizarOuInserirImagens(Produto produto, List<ProdutoImagem> novasImagens) {
        return novasImagens.stream()
                .map(imagem -> {
                    Optional<ProdutoImagem> produtoImagem = produtoImagemRepositorio.buscarPorProdutoIdETamanho(produto.getProdutoId(), imagem.getImgUrl());
                    if (produtoImagem.isPresent()) {
                        ProdutoImagem produtoImagemExistente = produtoImagem.get();
                        produtoImagemExistente.setTitulo(imagem.getTitulo());
                        return produtoImagemRepositorio.save(produtoImagemExistente);
                    } else {
                        ProdutoImagem novaImagem = new ProdutoImagem();
                        novaImagem.setProduto(produto);
                        novaImagem.setTitulo(imagem.getTitulo());
                        novaImagem.setImgUrl(imagem.getImgUrl());
                        return produtoImagemRepositorio.save(novaImagem);
                    }
                })
                .collect(Collectors.toList());
    }
}