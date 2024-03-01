package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.ProdutoResumidoDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.repositorios.*;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import ProjectExe.Integracao.servicos.utilitarios.Arquivos;
import ProjectExe.Integracao.servicos.utilitarios.Formatador;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    //exportar produtos para excel
    @Transactional(readOnly = true)
    public byte[] exportarParaExcel(){
        return arquivos.exportarProdutosParaExcel();
    }

    //inserir novo registro
    @Transactional
    public ProdutoDTO inserir(ProdutoDTO obj) {
        Produto produto = new Produto();
        atualizarDadosProduto(produto, obj);
        produto.setAtivo('N');
        return new ProdutoDTO(produtoRepositorio.save(produto));
    }

    //atualizar registro
    @Transactional
    public ProdutoDTO atualizar(Long produtoId, ProdutoDTO obj) {
        try {
            Produto produto = produtoRepositorio.getReferenceById(produtoId);
            atualizarDadosProduto(produto, obj);
            return new ProdutoDTO(produtoRepositorio.save(produto));
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
        Produto produto = produtoRepositorio.findById(produtoId)
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + produtoId + "  encontrado"));
        produto.getGrade().removeIf(produtoGrade -> produtoGrade.getTamanho().equals(tamanho));
        return new ProdutoDTO(produtoRepositorio.save(produto));
    }

    //Método utilizado no método de inserir e atualizar dados
    private void atualizarDadosProduto(Produto entidade, ProdutoDTO dto) {
        if (entidade.getProdutoId() == null) {
            entidade.setDataCadastro(Instant.now());
            entidade.setQtdVendida(0);
            entidade.setAtivo('N');
        } else {
            entidade.setDataAtualizacao(Instant.now());
            entidade.setAtivo(dto.getAtivo());
        }
        entidade.setNome(dto.getNome());
        entidade.setEan(dto.getEan());
        String ncmFormatado = Formatador.formatarNCM(dto.getNcm());
        entidade.setNcm(ncmFormatado);
        entidade.setReferencia(dto.getReferencia());
        entidade.setDescCurta(dto.getDescCurta());
        entidade.setDescLonga(dto.getDescLonga());
        entidade.setDataLancamento(dto.getDataLancamento());
        entidade.setEstoqueTotal(dto.getEstoqueTotal());
        entidade.setPreco(dto.getPreco());
        entidade.setPrecoProm(dto.getPrecoProm());
        entidade.setTempoGarantia(dto.getTempoGarantia());
        entidade.setMsgGarantia(dto.getMsgGarantia());
        entidade.setComprimento(dto.getComprimento());
        entidade.setLargura(dto.getLargura());
        entidade.setAltura(dto.getAltura());
        entidade.setPeso(dto.getPeso());

        Classe classe = carregarClasse(dto.getClasse());
        entidade.setClasse(classe);

        Marca marca = carregarMarca(dto.getMarca());
        entidade.setMarca(marca);

        List<Categoria> categorias = new ArrayList<>(carregarCategoria(dto.getCategorias()));
        entidade.getCategorias().clear();
        entidade.getCategorias().addAll(categorias);

        produtoRepositorio.save(entidade);
        Set<ProdutoGrade> grades = new HashSet<>(carregarGrade(entidade, dto.getGrade()));
        entidade.getGrade().addAll(grades);

        List<ProdutoImagem> imagems = new ArrayList<>(carregarImagens(entidade, dto.getImagens()));
        entidade.getImagens().addAll(imagems);
    }

    //Verifica se a classe existe para atualizar no produto
    private Classe carregarClasse(Classe classe) {
        return classeRepositorio.findById(classe.getClasseId())
                .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Classe " + classe.getClasseId() + " não existe"));
    }

    //Verifica, atualiza e cadastra a Marca, se necessário
    private Marca carregarMarca(Marca marca) {
        return marcaRepositorio.findByNome(marca.getNome())
                .orElseGet(() -> {
                    Marca novaMarca = new Marca();
                    novaMarca.setNome(marca.getNome());
                    return marcaRepositorio.save(novaMarca);
                });
    }

    //Verifica, atualiza e cadastra as Categorias, se necessário
    private Set<Categoria> carregarCategoria(List<Categoria> categorias) {
        Set<Categoria> categoriasProd = new HashSet<>();
        for (Categoria categoriaDTO : categorias) {
            Categoria categoria = categoriaRepositorio.findByNome(categoriaDTO.getNome())
                    .orElseGet(() -> {
                        Categoria novaCategoria = new Categoria();
                        novaCategoria.setNome(categoriaDTO.getNome());
                        return categoriaRepositorio.save(novaCategoria);
                    });
            categoriasProd.add(categoria);
        }
        return categoriasProd;
    }

    //Atualiza e insere novos tamanhos a grade produtos se necessário
    private List<ProdutoGrade> carregarGrade(Produto produto, Set<ProdutoGrade> grades) {
        return grades.stream()
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
                        ClasseGrade classeGradeProd = classeGrade.orElseGet(() -> {
                            ClasseGrade novaClasseGrade = new ClasseGrade();
                            novaClasseGrade.setClasse(produto.getClasse());
                            novaClasseGrade.setTamanho(grade.getTamanho());
                            return classeGradeRepositorio.save(novaClasseGrade);
                        });
                        ProdutoGrade novaGrade = new ProdutoGrade();
                        novaGrade.setProduto(produto);
                        novaGrade.setPreco(grade.getPreco());
                        novaGrade.setPrecoPromocional(grade.getPrecoPromocional());
                        novaGrade.setEan(grade.getEan());
                        novaGrade.setQuantidadeEstoque(grade.getQuantidadeEstoque());
                        novaGrade.setTamanho(classeGradeProd.getTamanho());
                        return produtoGradeRepositorio.save(novaGrade);
                    });
                })
                .collect(Collectors.toList());
    }

    //atualiza e insere novas imagens ao produto
    public List<ProdutoImagem> carregarImagens(Produto produto, List<ProdutoImagem> imagens) {
        return imagens.stream()
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