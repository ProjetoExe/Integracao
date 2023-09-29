package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.VendaItens;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class VendaItensResumidoDTO {

    private Produto produto;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal subTotal;
    private BigDecimal total;

    public VendaItensResumidoDTO() {
    }

    //Construtor com parâmetro da classe VendaItens para VendaItensResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaItensResumidoDTO(VendaItens entidade) { BeanUtils.copyProperties(entidade, this);
    }
}
