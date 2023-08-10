package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.ProdutoGradePK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto_grade")
@JsonPropertyOrder({"tamanho", "codigoDeBarra", "precoVista", "precoPrazo", "quantidadeEstoque"})
public class ProdutoGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoGradePK id = new ProdutoGradePK();
    private String codigoDeBarra;
    private BigDecimal precoVista;
    private BigDecimal precoPrazo;
    private Integer quantidadeEstoque;

    public ProdutoGrade(){
    }

    public ProdutoGrade(Produto produto, String tamanho, String codigoDeBarra, BigDecimal precoVista, BigDecimal precoPrazo, Integer quantidadeEstoque) {
        id.setProduto(produto);
        id.setTamanho(tamanho);
        this.codigoDeBarra = codigoDeBarra;
        this.precoVista = precoVista;
        this.precoPrazo = precoPrazo;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @JsonIgnore
    public Produto getProduto() { return id.getProduto(); }

    public void setProduto(Produto produto) { id.setProduto(produto); }

    public String getTamanho() { return id.getTamanho(); }

    public void setTamanho(String tamanho) { id.setTamanho(tamanho); }

    public String getCodigoDeBarra() { return codigoDeBarra; }

    public void setCodigoDeBarra(String codigoDeBarra) { this.codigoDeBarra = codigoDeBarra; }

    public BigDecimal getPrecoVista() { return precoVista; }

    public void setPrecoVista(BigDecimal precoVista) { this.precoVista = precoVista; }

    public BigDecimal getPrecoPrazo() { return precoPrazo; }

    public void setPrecoPrazo(BigDecimal precoPrazo) { this.precoPrazo = precoPrazo; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoGrade that = (ProdutoGrade) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
