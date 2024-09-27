package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Categoria;
import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.ProdutoImagem;
import ProjectExe.Integracao.entidades.enums.StatusAtivo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonPropertyOrder({"produtoId", "imgUrl", "nomeProd", "referencia", "categoria", "precoVenda", "estoqueTotal", "qtdVendida", "optAtivo"})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    private String nomeProd;
    private String referencia;
    private BigDecimal precoVenda;
    private Integer estoqueTotal;
    private Integer qtdVendida;
    private Boolean optAtivo;
    private String categoria;
    private String imgUrl;

    public ProdutoResumidoDTO(Long produtoId, String imgUrl, String nomeProd, String referencia, BigDecimal precoVenda, Integer estoqueTotal, Integer qtdVendida, Boolean optAtivo, String categoria) {
        this.produtoId = produtoId;
        this.imgUrl = imgUrl;
        this.nomeProd = nomeProd;
        this.referencia = referencia;
        this.precoVenda = precoVenda;
        this.estoqueTotal = estoqueTotal;
        this.qtdVendida = qtdVendida;
        this.optAtivo = optAtivo;
        this.categoria = categoria;
    }

    public StatusAtivo getOptAtivo() { return StatusAtivo.status((optAtivo)); }
}