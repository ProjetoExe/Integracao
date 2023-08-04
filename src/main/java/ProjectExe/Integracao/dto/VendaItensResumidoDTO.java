package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.VendaItens;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class VendaItensResumidoDTO {

    private Long cod_prod;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    //Construtor com parâmetro da classe VendaItens para VendaItensResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaItensResumidoDTO(VendaItens entidade) { BeanUtils.copyProperties(entidade, this);
        cod_prod = entidade.getProduto().getId();
    }

    public Long getCod_prod() { return cod_prod; }

    public void setCod_prod(Long cod_prod) { this.cod_prod = cod_prod; }

    public Integer getQuantidade() { return quantidade; }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPreco() { return preco; }

    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public BigDecimal getDesconto() { return desconto; }

    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }

    public BigDecimal getSubTotal() { return subTotal; }

    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }

    public BigDecimal getTotal() { return total; }

    public void setTotal(BigDecimal total) { this.total = total; }
}
