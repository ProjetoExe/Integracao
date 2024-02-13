package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.VendaItens;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class VendaItensDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long produtoId;
    private String nomeProd;
    private String variacaoProd;
    private Integer qtdVendida;
    private BigDecimal preco;
    private BigDecimal vlrDesc;
    private BigDecimal vlrTotal;

    //Construtor com parâmetro da classe VendaItens para VendaItensResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaItensDTO(VendaItens entidade) { BeanUtils.copyProperties(entidade, this);
        produtoId = entidade.getProduto().getProdutoId();
    }
}
