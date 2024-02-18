package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonPropertyOrder({"produtoId", "nome", "ean", "estoqueTotal", "preco", "precoPromocional", "ativo"})
@Getter
@Setter
@NoArgsConstructor
public class ProdutoResumidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    private String nome;
    private Long ean;
    private Integer estoqueTotal;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private char ativo;

    //Construtor com parâmetro da classe Produto para ProdutoDTO / BeanUtils necessita de setter além de getter no DTO
    public ProdutoResumidoDTO(Produto entidade){ BeanUtils.copyProperties(entidade, this);
    }
}