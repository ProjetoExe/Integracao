package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.enums.TipoProdutoAlteracao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AtualizacaoPrecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal vlrDesc;
    private Boolean acrescimo;
    private Boolean porPorcentagem;
    private Boolean arredondarVlr;
    private Boolean apliVariacoes;
    private Boolean apliProdProm;
    private BigDecimal vlrMinProd;
    private BigDecimal vlrMaxProd;
    private Integer tipoProd;

    private List<Long> lista = new ArrayList<>();

    public TipoProdutoAlteracao getTipoProdProm() { return TipoProdutoAlteracao.codigoStatus(tipoProd); }

    public void setTipoProd(TipoProdutoAlteracao promocaoTipo) {
        if (promocaoTipo != null) {
            this.tipoProd = promocaoTipo.getCodigo();
        }
    }
}