package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.*;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.VariacaoProduto;
import ProjectExe.Integracao.repositorios.*;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.utilitarios.Arquivos;
import ProjectExe.Integracao.servicos.utilitarios.Formatador;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @Autowired
    private Arquivos arquivos;

    //buscar por ID para edição e visualização detalhada
    @Transactional(readOnly = true)
    @Cacheable("produtos")
    public ProdutoDTO buscarPorId(Long produtoId) {
        Optional<Produto> resultado = produtoRepositorio.findById(produtoId);
        return resultado.map(ProdutoDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + " não encontrado"));
    }

    //buscar todos os registros com filtro de id, nome, categoria e ativo
    @Transactional(readOnly = true)
    @Cacheable("produtos")
    public Page<ProdutoResumidoDTO> buscarTodos(Long produtoId, String nome, String ref, Long ean, String marca, Integer optAtivo, List<String> categorias, Double precoInicial, Double precoFinal, Integer estoqueInicial, Integer estoqueFinal, Pageable pageable) {
        Page<ProdutoResumidoDTO> resultado = Page.empty();
        if (produtoId != null) {
            Optional<ProdutoResumidoDTO> produto = produtoRepositorio.buscarPorId(produtoId);
            if (produto.isPresent()) {
                resultado = new PageImpl<>(Collections.singletonList(produto.get()), pageable, 1);
            }
        } else {
            //String categoriasStr = categorias != null ? String.join(",", categorias) : null;
            resultado = produtoRepositorio.buscarTodos(nome, ref, ean, marca, optAtivo, categorias, precoInicial, precoFinal, estoqueInicial, estoqueFinal, pageable);
        }
        return resultado;
    }

    //exportar produtos para excel
    @Transactional(readOnly = true)
    public byte[] exportarParaExcel() {
        return arquivos.exportarProdutosParaExcel();
    }

    //inserir novo registro
    @CacheEvict(value = "produtos", allEntries = true)
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj) {
        try {
            Produto produto = new Produto();
            atualizarDadosProduto(produto, obj);
            return new ProdutoDTO(produtoRepositorio.save(produto));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //atualizar registro
    @CacheEvict(value = "produtos", allEntries = true)
    @Transactional
    public ProdutoDTO atualizar(Long produtoId, ProdutoDTO obj) {
        try {
            Produto produto = produtoRepositorio.getReferenceById(produtoId);
            atualizarDadosProduto(produto, obj);
            return new ProdutoDTO(produtoRepositorio.save(produto));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + " não encontrado");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //excluir um registro
    //@Transactional retirado pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "produtos", allEntries = true)
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
    public void removerGrade(Long produtoId, String tamanho) {
        ProdutoGrade produtoGrade = produtoGradeRepositorio.buscarPorProdutoIdEVariacao(produtoId, tamanho).
                orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Tamanho " + tamanho + " de Produto " + produtoId + " já excluído ou não encontrado!"));
        produtoGradeRepositorio.delete(produtoGrade);
    }

    //Método utilizado no método de inserir e atualizar dados
    private void atualizarDadosProduto(Produto entidade, ProdutoDTO dto) {
        if (entidade.getProdutoId() == null) {
            entidade.setDataCadastro(Instant.now());
            entidade.setOptAtivo(dto.getOptAtivo() != null ? dto.getOptAtivo() : Boolean.FALSE);
            entidade.setOptLancamento(dto.getOptLancamento() != null ? dto.getOptLancamento() : Boolean.FALSE);
            entidade.setOptPromocao(dto.getOptPromocao() != null ? dto.getOptPromocao() : Boolean.FALSE);
            entidade.setOptLancamento(dto.getOptLancamento() != null ? dto.getOptLancamento() : Boolean.FALSE);
            entidade.setOptFreteGratis(dto.getOptFreteGratis() != null ? dto.getOptFreteGratis() : Boolean.FALSE);
            entidade.setOptProdVirtual(dto.getOptProdVirtual() != null ? dto.getOptProdVirtual() : Boolean.FALSE);
            entidade.setOptDisponivel(dto.getOptDisponivel() != null ? dto.getOptDisponivel() : Boolean.FALSE);
            entidade.setDataAtivacao(dto.getOptAtivo() ? Instant.now() : null);
            entidade.setQtdVendida(0);
        } else {
            entidade.setDataAtualizacao(Instant.now());
            entidade.setDataAtivacao(!entidade.getOptAtivo() && dto.getOptAtivo() ? dto.getDataAtivacao() : entidade.getDataAtivacao());
        }
        entidade.setOptAtivo(dto.getOptAtivo());
        entidade.setOptLancamento(dto.getOptLancamento());
        entidade.setOptPromocao(dto.getOptPromocao());
        entidade.setOptLancamento(dto.getOptLancamento());
        entidade.setOptFreteGratis(dto.getOptFreteGratis());
        entidade.setOptProdVirtual(dto.getOptProdVirtual());
        entidade.setDataDesativacao(entidade.getOptAtivo() && !dto.getOptAtivo() ? dto.getDataDesativacao() : entidade.getDataDesativacao());
        entidade.setNomeProd(dto.getNomeProd());
        entidade.setEan(dto.getEan());
        String ncmFormatado = Formatador.formatarNCM(dto.getNcm());
        entidade.setNcm(ncmFormatado);
        entidade.setReferencia(dto.getReferencia());
        entidade.setDescCurta(dto.getDescCurta());
        entidade.setDescLonga(dto.getDescLonga());
        entidade.setModelo(dto.getModelo());
        entidade.setItensIncluso(dto.getItensIncluso());
        entidade.setDataLancamento(dto.getDataLancamento());
        entidade.setQtdEstoque(dto.getQtdEstoque());
        entidade.setPrecoCusto(dto.getPrecoCusto());
        entidade.setPrecoVenda(dto.getPrecoVenda());
        entidade.setPrecoProm(dto.getPrecoProm());
        entidade.setTempoGarantia(dto.getTempoGarantia());
        entidade.setMsgGarantia(dto.getMsgGarantia());
        entidade.setComprimento(dto.getComprimento());
        entidade.setLargura(dto.getLargura());
        entidade.setAltura(dto.getAltura());
        entidade.setPeso(dto.getPeso());

        carregarClasse(entidade, dto.getClasse());
        carregarMarca(entidade, dto.getMarca());
        carregarCategoria(entidade, dto.getCategoria());
        produtoRepositorio.save(entidade);

        carregarSubCategorias(entidade, dto.getSubCategorias());
        carregarImagens(entidade, dto.getImagens());
        carregarGrade(entidade, dto.getGrade());

        entidade.setEstoqueTotal(atualizarEstoqueTotal(entidade.getProdutoId()));
        entidade.setOptDisponivel(entidade.getEstoqueTotal() > 0);
        produtoRepositorio.save(entidade);
    }

    //Atualizar e atualizar o campo estoqueTotal do produto conforme inserção ou atualização do mesmo
    private Integer atualizarEstoqueTotal(Long produtoId) {
        List<ProdutoGrade> produtoGrade = produtoGradeRepositorio.buscarPorProdutoId(produtoId);
        return produtoGrade.stream()
                .mapToInt(ProdutoGrade::getQtdEstoque)
                .sum();
    }

    //Verifica se a classe existe para atualizar no produto
    private void carregarClasse(Produto produto, ClasseDTO classeDTO) {
        Classe classe = classeRepositorio.findById(classeDTO.getClasseId()).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Classe " + classeDTO.getClasseId() + " não existe"));
        produto.setClasse(classe);
    }

    //Verifica, atualiza e cadastra a Marca, se necessário
    private void carregarMarca(Produto produto, MarcaDTO marcaDTO) {
        Marca marca = marcaRepositorio.findByNomeMarca(marcaDTO.getNomeMarca()).orElseGet(() -> {
            Marca novaMarca = new Marca();
            novaMarca.setNomeMarca(marcaDTO.getNomeMarca());
            return marcaRepositorio.save(novaMarca);
        });
        produto.setMarca(marca);
    }

    private void carregarCategoria(Produto produto, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepositorio.findByNomeCat(categoriaDTO.getNomeCat()).orElseGet(() -> {
            Categoria novaCategoria = new Categoria();
            novaCategoria.setNomeCat(categoriaDTO.getNomeCat());
            return categoriaRepositorio.save(novaCategoria);
        });
        produto.setCategoria(categoria);
    }

    //Verifica, atualiza e cadastra as Categorias, se necessário
    private void carregarSubCategorias(Produto produto, Set<CategoriaDTO> categoriasDTO) {
        Set<Categoria> categorias = new HashSet<>();
        for (CategoriaDTO categoriaDTO : categoriasDTO) {
            Categoria categoria = categoriaRepositorio.findByNomeCat(categoriaDTO.getNomeCat()).orElseGet(() -> {
                Categoria novaCategoria = new Categoria();
                novaCategoria.setNomeCat(categoriaDTO.getNomeCat());
                return categoriaRepositorio.save(novaCategoria);
            });
            categorias.add(categoria);
        }
        produto.getSubCategorias().clear();
        produto.getSubCategorias().addAll(categorias);
    }

    //Atualiza e insere novos tamanhos a grade produtos se necessário
    private void carregarGrade(Produto produto, Set<ProdutoGradeDTO> gradesDTO) {
        Set<ProdutoGrade> grades = gradesDTO.stream().map(grade -> {
            Optional<ProdutoGrade> produtoGrade;
            if (produto.getVariacaoProduto() == VariacaoProduto.VARIACAO_DUPLA) {
                produtoGrade = produtoGradeRepositorio.buscarPorProdutoIdEVariacaoDupla(produto.getProdutoId(), grade.getVariacao(), grade.getVariacaoDupla());
            } else {
                produtoGrade = produtoGradeRepositorio.buscarPorProdutoIdEVariacao(produto.getProdutoId(), grade.getVariacao());
            }
            return produtoGrade.map(produtoExistente -> {
                produtoExistente.setPrecoVenda(grade.getPrecoVenda());
                produtoExistente.setPrecoProm(grade.getPrecoProm());
                produtoExistente.setEan(grade.getEan());
                produtoExistente.setQtdEstoque(grade.getQtdEstoque());
                return produtoGradeRepositorio.save(produtoExistente);
            }).orElseGet(() -> {
                Optional<ClasseGrade> classeGrade = classeGradeRepositorio.buscarPorClasseETamanho(grade.getVariacao());
                ClasseGrade classeGradeProd = classeGrade.orElseGet(() -> {
                    ClasseGrade novaClasseGrade = new ClasseGrade();
                    novaClasseGrade.setClasse(produto.getClasse());
                    novaClasseGrade.setVariacao(grade.getVariacao());
                    return classeGradeRepositorio.save(novaClasseGrade);
                });
                ProdutoGrade novaGrade = novoProdutoGrade(produto, grade, classeGradeProd);
                return produtoGradeRepositorio.save(novaGrade);
            });
        }).collect(Collectors.toSet());
        produto.getGrade().addAll(grades);
    }

    //criar nova grade de produto (novo tamanho ou variação)
    private static ProdutoGrade novoProdutoGrade(Produto produto, ProdutoGradeDTO grade, ClasseGrade classeGradeProd) {
        ProdutoGrade novaGrade = new ProdutoGrade();
        novaGrade.setProduto(produto);
        novaGrade.setPrecoVenda(grade.getPrecoVenda());
        novaGrade.setPrecoProm(grade.getPrecoProm());
        novaGrade.setEan(grade.getEan());
        novaGrade.setQtdEstoque(grade.getQtdEstoque());
        novaGrade.setVariacao(classeGradeProd.getVariacao());
        novaGrade.setVariacaoDupla(produto.getVariacaoProduto() == VariacaoProduto.VARIACAO_DUPLA ? grade.getVariacaoDupla() : null);
        return novaGrade;
    }

    //atualiza e insere novas imagens ao produto
    public void carregarImagens(Produto produto, List<ProdutoImagemDTO> imagensDTO) {
        List<ProdutoImagem> imagens = imagensDTO.stream().map(imagem -> {
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
        }).toList();
        produto.getImagens().addAll(imagens);
    }
}