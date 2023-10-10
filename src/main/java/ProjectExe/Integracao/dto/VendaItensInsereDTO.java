package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Produto;
import ProjectExe.Integracao.entidades.VendaItens;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class VendaItensInsereDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Produto produto;
    private String tamanho;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal total;

    public VendaItensInsereDTO() {
    }

    //Construtor com parâmetro da classe VendaItens para VendaItensResumidoDTO / BeanUtils necessita de setter além de getter no DTO
    public VendaItensInsereDTO(VendaItens entidade) { BeanUtils.copyProperties(entidade, this); }
}
