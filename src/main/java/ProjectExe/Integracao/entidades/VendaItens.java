package ProjectExe.Integracao.entidades;

import ProjectExe.Integracao.entidades.pk.VendaItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "venda_itens")
public class VendaItens implements Serializable {
    private static final long SerialVersionUID = 1L;

    @EmbeddedId
    private VendaItemPK id = new VendaItemPK();

    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    public VendaItens(){
    }

    public VendaItens(Venda venda, Produto produto, Integer quantidade, BigDecimal preco, BigDecimal desconto, BigDecimal subTotal, BigDecimal total) {
        id.setVenda(venda);
        id.setProduto(produto);
        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;
        this.subTotal = subTotal;
        this.total = total;
    }

    @JsonIgnore //Get e Set de Venda e Produto recebem a Venda e os Produtos e atribuem dentro da OrderItemPK com as respectivas chaves compostas
    public Venda getVenda(){
        return id.getVenda();
    }

    public void setVenda(Venda venda){
        id.setVenda(venda);
    }

    @JsonIgnoreProperties({"grade", "imgUrl", "ativo"}) //Ignora a coleção de grade do Produto ao puxar o Objeto Venda
    public Produto getProduto(){
        return id.getProduto();
    }

    public void setProduto(Produto produto){
        id.setProduto(produto);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    //Calcula o subTotal retornando o produto - desconto * quantidade
    public BigDecimal getSubTotal(){
        BigDecimal soma = preco.multiply(new BigDecimal(quantidade));
        return soma;
    }

    //Calcula o total retornando o produto - desconto * quantidade
    public BigDecimal getTotal(){
        BigDecimal soma = (preco.subtract(desconto));
        BigDecimal total = soma.multiply(new BigDecimal(quantidade));
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaItens that = (VendaItens) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
