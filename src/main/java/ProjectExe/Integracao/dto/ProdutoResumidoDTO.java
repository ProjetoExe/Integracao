package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.enums.StatusAtivo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonPropertyOrder({"produtoId", "optAtivo", "nome", "preco", "estoqueTotal", "qtdVendida",})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    private Integer optAtivo;
    private String nome;
    private BigDecimal preco;
    private Integer estoqueTotal;
    private Integer qtdVendida;

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoResumidoDTO(Produto entidade){
        BeanUtils.copyProperties(entidade, this);
    }

    public StatusAtivo getOptAtivo() { return StatusAtivo.status((optAtivo)); }
}