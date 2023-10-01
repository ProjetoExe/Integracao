package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.VendaItens;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class VendaItensDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    public VendaItensDTO(){
    }

    //Construtor com parâmetro da classe VendaItens para VendaItensResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaItensDTO(VendaItens entidade) { BeanUtils.copyProperties(entidade, this);
        productId = entidade.getProduto().getProdutoId();
    }
}
