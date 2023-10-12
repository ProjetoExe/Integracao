package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.VendaItemPK;
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
import java.math.BigDecimal;

@Entity
@Table(name = "venda_itens")
@NoArgsConstructor
@Setter //Não adicionado o @Getter no id pelo conflito com o @JsonIgnore do 'getVenda', está em utilização o VendaItensResumidoDTO que não contém a associação com a Venda
@EqualsAndHashCode(of="id")
public class VendaItens implements Serializable {
    private static final long SerialVersionUID = 1L;

    @EmbeddedId
    private VendaItemPK id = new VendaItemPK();
    @Getter
    private String nomeProduto;
    @Getter
    private String tamanho;
    @Getter
    private Integer quantidade;
    @Getter
    private BigDecimal preco;
    @Getter
    private BigDecimal desconto;
    @Getter
    private BigDecimal total;

    public VendaItens(Venda venda, Produto produto, String nomeProduto, String tamanho, Integer quantidade, BigDecimal preco, BigDecimal desconto, BigDecimal subTotal, BigDecimal total) {
        id.setVenda(venda);
        id.setProduto(produto);
        this.nomeProduto = nomeProduto;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;
        this.total = total;
    }

    @JsonIgnore
    public Venda getVenda(){ return id.getVenda(); }

    public void setVenda(Venda venda){ id.setVenda(venda); }

    @JsonIgnoreProperties({"grade", "imgUrl", "ativo"}) //Ignora a coleção de grade e os campos imgUrl e ativo do Produto ao puxar o Objeto Venda
    public Produto getProduto(){ return id.getProduto(); }

    public void setProduto(Produto produto){ id.setProduto(produto); }
}