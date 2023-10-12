package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.ProdutoGradePK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "produto_grade")
@NoArgsConstructor
@Setter //Não adicionado o @Getter pelo conflito com o @JsonIgnore do 'getProduto'
@EqualsAndHashCode(of="id")
@JsonPropertyOrder({"tamanho", "codigoDeBarra", "precoVista", "precoPrazo", "quantidadeEstoque"})
public class ProdutoGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoGradePK id = new ProdutoGradePK();
    @Getter
    private Long ean;
    @Getter
    private BigDecimal preco;
    @Getter
    private BigDecimal precoPromocional;
    @Getter
    private Integer quantidadeEstoque;

    public ProdutoGrade(Produto produto, String tamanho, Long ean, BigDecimal preco, BigDecimal precoPromocional, Integer quantidadeEstoque) {
        id.setProduto(produto);
        id.setTamanho(tamanho);
        this.ean = ean;
        this.preco = preco;
        this.precoPromocional = precoPromocional;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @JsonIgnore
    public Produto getProduto() { return id.getProduto(); }

    public void setProduto(Produto produto) { id.setProduto(produto); }

    public String getTamanho() { return id.getTamanho(); }

    public void setTamanho(String tamanho) { id.setTamanho(tamanho); }
}