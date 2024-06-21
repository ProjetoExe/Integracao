package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.PromocaoItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "promocao_itens")
@NoArgsConstructor
@Setter //Não adicionado o @Getter no id pelo conflito com o @JsonIgnore do 'getVenda', está em utilização o VendaItensResumidoDTO que não contém a associação com a Venda
@EqualsAndHashCode(of="id")
public class PromocaoItens implements Serializable {
    private static final long SerialVersionUID = 1L;

    @EmbeddedId
    private PromocaoItemPK id = new PromocaoItemPK();
    @Getter
    private String nomeProd;

    public PromocaoItens(Promocao promocao, Produto produto) {
        id.setPromocao(promocao);
        id.setProduto(produto);
    }

    @JsonIgnore
    public Promocao getPromocao(){ return id.getPromocao(); }

    public void setPromocao(Promocao promocao){ id.setPromocao(promocao); }

    @JsonIgnoreProperties({"grade", "imgUrl", "ativo"}) //Ignora a coleção de grade e os campos imgUrl e ativo do Produto ao puxar o Objeto Promocao
    public Produto getProduto(){ return id.getProduto(); }

    public void setProduto(Produto produto){ id.setProduto(produto); }
}
