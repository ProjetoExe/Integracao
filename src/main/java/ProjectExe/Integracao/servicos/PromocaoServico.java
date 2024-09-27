package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.dto.PromocaoDTO;
import ProjectExe.Integracao.dto.PromocaoResumidaDTO;
import ProjectExe.Integracao.entidades.*;
import ProjectExe.Integracao.entidades.enums.TipoProdutoAlteracao;
import ProjectExe.Integracao.repositorios.ProdutoGradeRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import ProjectExe.Integracao.repositorios.PromocaoRepositorio;
import ProjectExe.Integracao.servicos.excecao.ExcecaoBancoDeDados;
import ProjectExe.Integracao.servicos.excecao.ExcecaoRecursoNaoEncontrado;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PromocaoServico {

    @Autowired
    private PromocaoRepositorio promocaoRepositorio;
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private ProdutoGradeRepositorio produtoGradeRepositorio;

    //busca promoções por ID detalhadamente (somente no botão editar)
    public PromocaoDTO buscarPorId(Long promocaoId) {
        Optional<Promocao> resultado = promocaoRepositorio.findById(promocaoId);
        return resultado.map(PromocaoDTO::new).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Promoção " + promocaoId + " não encontrada"));
    }

    //buscar todos os registros com filtro de id ou data
    @Transactional(readOnly = true)
    @Cacheable("promocoes")
    public Page<PromocaoResumidaDTO> buscarTodos(Long promocaoId, String minData, String maxData, Pageable pageable) {
        Instant dataInicial = minData.isBlank() ? LocalDate.now().minusDays(360).atStartOfDay().toInstant(ZoneOffset.UTC) :
                LocalDate.parse(minData, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant dataFinal = maxData.isBlank() ? LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC) :
                LocalDate.parse(maxData, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);

        Page<PromocaoResumidaDTO> resultado = Page.empty();
        return resultado = promocaoRepositorio.buscarTodos(promocaoId, dataInicial, dataFinal, pageable);
    }

    //inserir uma promoção
    @CacheEvict(value = "promocoes", allEntries = true)
    @Transactional
    public PromocaoDTO inserir(PromocaoDTO obj) {
        Promocao promocao = new Promocao();
        atualizarDados(promocao, obj);
        return new PromocaoDTO(promocaoRepositorio.save(promocao));
    }

    //Sem possibilidade de atualizar. Ao criar nova promoção substituirá promoção anterior.

    //excluir uma promocao
    //@Transactional //retirado, pois conflita com a exceção DataIntegrityViolantionException, impedindo-a de lançar a exceção personalizada
    @CacheEvict(value = "promocoes", allEntries = true)
    public void deletar(Long promocaoId) {
        try {
            estornarPromocao(promocaoId);
            promocaoRepositorio.deleteById(promocaoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Promoção " + promocaoId + " não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    //inserir dados das promoções
    private void atualizarDados(Promocao entidade, PromocaoDTO dto) {
        if (entidade.getPromocaoId() == null) {
            entidade.setDataCadastro(Instant.now());
        } else {
            entidade.setDataAtualizacao(Instant.now());
        }
        entidade.setNomeProm(dto.getNomeProm());
        entidade.setDataInicioProm(dto.getDataInicioProm());
        entidade.setDataFimProm(dto.getDataFimProm());
        entidade.setVlrDesc(dto.getVlrDesc());
        entidade.setPorPorcentagem(dto.getPorPorcentagem());
        entidade.setArredondarVlr(dto.getArredondarVlr());
        entidade.setApliVariacoes(dto.getApliVariacoes());
        entidade.setVlrMinProd(dto.getVlrMinProd());
        entidade.setVlrMaxProd(dto.getVlrMaxProd());
        entidade.setTipoProdProm(dto.getTipoProdProm());
        promocaoRepositorio.save(entidade);

        List<ProdutoDTO> produtos = new ArrayList<>();
        if (entidade.getTipoProdProm() == TipoProdutoAlteracao.TODOS) {
            produtos = produtoRepositorio.buscarTodosProdutos();
            carregarProdutos(entidade, produtos);
            aplicarPromocaoProdutos(entidade, produtos);
        } else if (entidade.getTipoProdProm() == TipoProdutoAlteracao.POR_CATEGORIAS) {
            produtos = produtoRepositorio.buscarPorCategoria(dto.getLista());
            carregarProdutos(entidade, produtos);
            aplicarPromocaoProdutos(entidade, produtos);
        } else {
            produtos = produtoRepositorio.buscarPorId(dto.getLista());
            carregarProdutos(entidade, produtos);
            aplicarPromocaoProdutos(entidade, produtos);
        }
    }

    //insere os produtos na PromocaoItens
    private void carregarProdutos(Promocao entidade, List<ProdutoDTO> produtosDTO) {
        List<PromocaoItens> produtos = new ArrayList<>();
        for (ProdutoDTO itemDTO : produtosDTO) {
            Produto produto = produtoRepositorio.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Produto " + itemDTO.getProdutoId() + " não encontrado"));
            PromocaoItens promocaoItens = new PromocaoItens();
            promocaoItens.setProduto(produto);
            promocaoItens.setPromocao(entidade);
            promocaoItens.setNomeProd(produto.getNomeProd());
            produtos.add(promocaoItens);
        }
        entidade.getPromocaoItens().addAll(produtos);
    }

    //aplica a promoção aos produtos ao salvar
    private void aplicarPromocaoProdutos(Promocao entidade, List<ProdutoDTO> produtos) {
        for (ProdutoDTO produtoDTO : produtos) {
            Produto produto = produtoRepositorio.getReferenceById(produtoDTO.getProdutoId());
            BigDecimal vlrPromocional = calcularPromocao(entidade, produto);
            produto.setPromocaoId(entidade.getPromocaoId());
            produto.setPrecoProm(vlrPromocional);
            produto.setDataInicioProm(entidade.getDataInicioProm());
            produto.setDataFimProm(entidade.getDataFimProm());
            produto.setOptPromocao(true);
            atualizarVariacoes(produto, vlrPromocional, entidade);
            produtoRepositorio.save(produto);
        }
    }

    //aplica alteração também nas variações
    private void atualizarVariacoes(Produto produto, BigDecimal novoVlr, Promocao dto) {
        if (dto.getApliVariacoes()) {
            List<ProdutoGrade> variacoes = produtoGradeRepositorio.buscarPorProdutoId(produto.getProdutoId());
            if (!variacoes.isEmpty()) {
                for (ProdutoGrade produtoGrade : variacoes) {
                    produtoGrade.setPrecoProm(novoVlr);
                    produtoGrade.setDataInicioProm(produto.getDataInicioProm());
                    produtoGrade.setDataFimProm(produto.getDataFimProm());
                }
            }
        }
    }

    //calcula os valores da promoção
    private BigDecimal calcularPromocao(Promocao entidade, Produto produto) {
        BigDecimal vlrOriginal = produto.getPrecoVenda();
        BigDecimal vlrPromocional;

        if (entidade.getPorPorcentagem()){
            vlrPromocional = vlrOriginal.subtract(vlrOriginal.multiply(entidade.getVlrDesc()).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP));
        } else {
            vlrPromocional = vlrOriginal.subtract(entidade.getVlrDesc());
        }
        if (entidade.getArredondarVlr()){
            vlrPromocional = vlrPromocional.setScale(0, RoundingMode.HALF_UP);
        }
        return vlrPromocional;
    }

    //estorna preços originais ao excluir promoção
    private void estornarPromocao(Long promocaoId) {
        Promocao promocao = promocaoRepositorio.findById(promocaoId).orElseThrow(() -> new ExcecaoRecursoNaoEncontrado("Promoção " + promocaoId + " não encontrada"));
        for (PromocaoItens promocaoItem : promocao.getPromocaoItens()) {
            Produto produto = promocaoItem.getProduto();

            produto.setPrecoProm(null);
            produto.setDataInicioProm(null);
            produto.setDataFimProm(null);
            produto.setPromocaoId(null);
            produto.setOptPromocao(false);
            if (promocao.getApliVariacoes()) {
                List<ProdutoGrade> variacoes = produtoGradeRepositorio.buscarPorProdutoId(produto.getProdutoId());
                if (!variacoes.isEmpty()) {
                    for (ProdutoGrade produtoGrade : variacoes) {
                        produtoGrade.setPrecoProm(null);
                        produtoGrade.setDataInicioProm(null);
                        produtoGrade.setDataFimProm(null);
                    }
                }
            }
            produtoRepositorio.save(produto);
        }
    }
}