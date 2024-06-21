package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.ProdutoGrade;
import ProjectExe.Integracao.entidades.pk.ProdutoGradePK;
import jakarta.persistence.EmbeddedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoGradeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId; //campo do ProdutoGradePK
    private String variacao; //campo do ProdutoGradePK

    private String variacaoDupla; // para produtos com variação dupla, caso não seja, será null
    private Long ean;
    private String referencia;
    private BigDecimal margemCusto;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private BigDecimal precoProm;
    private Integer qtdEstoque;
    private Integer qtdMinima;
    private Instant dataInicioProm;
    private Instant dataFimProm;

    public ProdutoGradeDTO(ProdutoGrade produtoGrade) {
        BeanUtils.copyProperties(produtoGrade, this);
        this.produtoId = produtoGrade.getProduto().getProdutoId();
        this.variacao = produtoGrade.getVariacao();
    }
}

