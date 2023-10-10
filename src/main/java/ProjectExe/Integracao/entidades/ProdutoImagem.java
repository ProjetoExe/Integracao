package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.ProdutoImagemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity //Não adicionado @Getter e @Setter pois ambos são personalizados e já estão implementados, incluindo o conflito gerando com @JsonIgnore de 'getProduto'
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@Table(name = "produto_imagem")  //Lembrar de rever a lógica, pois, não iremos receber nem passar imagem por JSON, apenas anexo direto na plataforma
public class ProdutoImagem implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoImagemPK id = new ProdutoImagemPK();
    private String titulo;
    private Integer ordem;

    public ProdutoImagem(Produto produto, String imgUrl, String titulo, Integer ordem) {
        id.setProduto(produto);
        id.setImgUrl(imgUrl);
        this.ordem = ordem;
        this.titulo = titulo;
    }

    @JsonIgnore
    public Produto getProduto() { return id.getProduto(); }

    public void setProduto(Produto produto) { id.setProduto(produto); }

    public String getImgUrl() { return id.getImgUrl(); }

    public void setImgUrl(String imgUrl) { id.setImgUrl(imgUrl); }

    public Integer getOrdem() { return ordem; }

    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
}