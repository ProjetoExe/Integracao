package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.ProdutoGradePK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "produto_grade")
@NoArgsConstructor
@Setter //Não adicionado o @Getter pelo conflito com o @JsonIgnore do 'getProduto'
@EqualsAndHashCode(of="id")
@JsonPropertyOrder({"variacao", "variacaoDupla", "referencia", "ean", "margemCusto", "precoCusto", "precoVenda", "precoProm", "qtdEstoque", "qtdMinima"})
public class ProdutoGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoGradePK id = new ProdutoGradePK();

    @Getter
    private String variacaoDupla; // para produtos com variação dupla, caso não seja, será null
    @Getter
    private Long ean;
    @Getter
    private String referencia;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal margemCusto;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal precoCusto;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal precoVenda;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal precoProm;
    @Getter
    private Integer qtdEstoque;
    @Getter
    private Integer qtdMinima;
    @Getter
    private Instant dataInicioProm;
    @Getter
    private Instant dataFimProm;

    public ProdutoGrade(Produto produto, String variacao, String variacaoDupla, Long ean, BigDecimal margemCusto, BigDecimal precoCusto, BigDecimal precoVenda, BigDecimal precoProm, BigDecimal precoPromocional, Integer qtdEstoque, Integer qtdMinima) {
        id.setProduto(produto);
        id.setVariacao(variacao);
        this.variacaoDupla = variacaoDupla;
        this.ean = ean;
        this.margemCusto = margemCusto;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.precoProm = precoPromocional;
        this.qtdEstoque = qtdEstoque;
        this.qtdMinima = qtdMinima;
    }

    @JsonIgnore
    public Produto getProduto() { return id.getProduto(); }

    public void setProduto(Produto produto) { id.setProduto(produto); }

    public String getVariacao() { return id.getVariacao(); }

    public void setVariacao(String tamanho) { id.setVariacao(tamanho); }

    public void atualizarEstoque(ProdutoGrade produtoGrade, Integer qtdVendida) {
        produtoGrade.qtdEstoque -= qtdVendida;
    }
}