package ProjectExe.Integracao.entidades.pk;

import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProdutoGradePK implements Serializable {
    private static final long SerialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @JsonProperty("Grade")
    @Column(name = "tamanho")
    private String tamanho;

    public Produto getProduto() { return produto; }

    public void setProduto(Produto produto) { this.produto = produto; }

    public String getTamanho() { return tamanho; }

    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoGradePK that = (ProdutoGradePK) o;
        return Objects.equals(produto, that.produto) && Objects.equals(tamanho, that.tamanho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, tamanho);
    }
}
