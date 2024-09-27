package ProjectExe.Integracao.servicos;

import ProjectExe.Integracao.dto.AtualizacaoPrecoDTO;
import ProjectExe.Integracao.dto.ProdutoDTO;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.ProdutoGrade;
import ProjectExe.Integracao.entidades.enums.TipoProdutoAlteracao;
import ProjectExe.Integracao.repositorios.ProdutoGradeRepositorio;
import ProjectExe.Integracao.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrecoServico {

    @Autowired
    ProdutoRepositorio produtoRepositorio;
    @Autowired
    ProdutoGradeRepositorio produtoGradeRepositorio;

    public void atualizarPrecos(AtualizacaoPrecoDTO dto) {
        List<ProdutoDTO> produtos = new ArrayList<>();
        if (dto.getTipoProdProm() == TipoProdutoAlteracao.TODOS) {
            produtos = produtoRepositorio.buscarTodosProdutos();
            aplicarPrecoProdutos(produtos, dto);
        } else if (dto.getTipoProdProm() == TipoProdutoAlteracao.POR_CATEGORIAS) {
            produtos = produtoRepositorio.buscarPorCategoria(dto.getLista());
            aplicarPrecoProdutos(produtos, dto);
        } else {
            produtos = produtoRepositorio.buscarPorId(dto.getLista());
            aplicarPrecoProdutos(produtos, dto);
        }
    }

    //aplica o novo preço aos produtos ao salvar
    private void aplicarPrecoProdutos(List<ProdutoDTO> produtos, AtualizacaoPrecoDTO dto) {
        for (ProdutoDTO produtoDTO : produtos) {
            Produto produto = produtoRepositorio.getReferenceById(produtoDTO.getProdutoId());
            if (dto.getApliProdProm()) { // true, aplica a todos os produtos
                BigDecimal novoVlr = calcularPrecoProdutos(produto, dto);
                produto.setPrecoVenda(novoVlr);
                atualizarVariacoes(produto, novoVlr, dto);
            }
            else { // false, atualiza somente produtos que não são promocionais
                if (!produto.getOptPromocao()) {
                    BigDecimal novoVlr = calcularPrecoProdutos(produto, dto);
                    produto.setPrecoVenda(novoVlr);
                    atualizarVariacoes(produto, novoVlr, dto);
                }
            }
            produtoRepositorio.save(produto);
        }
    }

    //aplica alteração também nas variações
    private void atualizarVariacoes(Produto produto, BigDecimal novoVlr, AtualizacaoPrecoDTO dto) {
        if (dto.getApliVariacoes()) {
            List<ProdutoGrade> variacoes = produtoGradeRepositorio.buscarPorProdutoId(produto.getProdutoId());
            if (!variacoes.isEmpty()) {
                for (ProdutoGrade produtoGrade : variacoes) {
                    produtoGrade.setPrecoVenda(novoVlr);
                }
            }
        }
    }

    //calcula os novos valores
    private BigDecimal calcularPrecoProdutos(Produto produto, AtualizacaoPrecoDTO dto) {
        BigDecimal vlrOriginal = produto.getPrecoVenda();
        BigDecimal vlrDesc = (!dto.getAcrescimo() ? dto.getVlrDesc().negate() : dto.getVlrDesc()); //se acréscimo for false, subtrair (mais com menos), se true, somar (mais com mais)
        BigDecimal novoVlr;

        if (dto.getPorPorcentagem()){
            novoVlr = vlrOriginal.add(vlrOriginal.multiply(vlrDesc.divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP)));
        } else {
            novoVlr = vlrOriginal.add(vlrDesc);
        }
        if (dto.getArredondarVlr()){
            novoVlr = novoVlr.setScale(0, RoundingMode.HALF_UP);
        }
        return novoVlr;
    }
}