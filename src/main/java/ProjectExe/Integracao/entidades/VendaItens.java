package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.dto.VendaItensDTO;
import ProjectExe.Integracao.entidades.pk.VendaItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@Entity
@Table(name = "venda_itens")
@NoArgsConstructor
@Setter //Não adicionado o @Getter no id pelo conflito com o @JsonIgnore do 'getVenda', está em utilização o VendaItensResumidoDTO que não contém a associação com a Venda
@EqualsAndHashCode(of="id")
public class VendaItens implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VendaItemPK id = new VendaItemPK();
    @Getter
    private String nomeProd;
    @Getter
    private String variacaoProd;
    @Getter
    private Integer qtdVendida;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrProd;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrDesc;
    @Getter
    @Column(precision = 10, scale = 2)
    private BigDecimal vlrTotal;

    public VendaItens(Venda venda, Produto produto, String nomeProd, String variacaoProd, Integer qtdVendida, BigDecimal vlrProd, BigDecimal vlrDesc, BigDecimal total) {
        id.setVenda(venda);
        id.setProduto(produto);
        this.nomeProd = nomeProd;
        this.variacaoProd = variacaoProd;
        this.qtdVendida = qtdVendida;
        this.vlrProd = vlrProd;
        this.vlrDesc = vlrDesc;
        this.vlrTotal = total;
    }

    public VendaItens(VendaItensDTO itemDTO) {
        this.setVariacaoProd(itemDTO.getVariacaoProd());
        this.setQtdVendida(itemDTO.getQtdVendida());
        this.setVlrProd(itemDTO.getVlrProd());
        this.setVlrDesc(itemDTO.getVlrDesc());
        this.setVlrTotal(itemDTO.getVlrTotal());
    }

    @JsonIgnore
    public Venda getVenda(){ return id.getVenda(); }

    public void setVenda(Venda venda){ id.setVenda(venda); }

    @JsonIgnoreProperties({"grade", "imgUrl", "ativo"}) //Ignora a coleção de grade e os campos imgUrl e ativo do Produto ao puxar o Objeto Venda
    public Produto getProduto(){ return id.getProduto(); }

    public void setProduto(Produto produto){ id.setProduto(produto); }
}