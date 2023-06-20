package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.ProdutoImagemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "produto_imagem")
public class ProdutoImagem implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoImagemPK id = new ProdutoImagemPK();

    public ProdutoImagem() {
    }

    public ProdutoImagem(Produto produto, String imgUrl) {
        id.setProduto(produto);
        id.setImgUrl(imgUrl);
    }

    @JsonIgnore
    public Produto getProduto() { return id.getProduto(); }

    public void setProduto(Produto produto) { id.setProduto(produto); }

    public String getImgUrl() { return id.getImgUrl(); }

    public void setImgUrl(String imgUrl) { id.setImgUrl(imgUrl); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoImagem that = (ProdutoImagem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
