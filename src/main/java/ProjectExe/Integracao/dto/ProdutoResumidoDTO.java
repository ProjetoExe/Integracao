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

@JsonPropertyOrder({"produtoId", "imagem", "nomeProd", "referencia", "categoria", "precoVenda", "estoqueTotal", "qtdVendida", "optAtivo"})
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

    @JsonIgnoreProperties("titulo")
    @JsonUnwrapped
    private ProdutoImagem imagem;

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoResumidoDTO(Produto entidade){
        BeanUtils.copyProperties(entidade, this);
        categoria = entidade.getCategoria().getNomeCat();
        imagem = (!entidade.getImagens().isEmpty() ? entidade.getImagens().get(0) : null); //pega a primeira imagem da lista
    }

    public StatusAtivo getOptAtivo() { return StatusAtivo.status((optAtivo)); }
}