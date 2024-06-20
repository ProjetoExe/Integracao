package ProjectExe.Integracao.dto;

import ProjectExe.Integracao.entidades.Cupom;
import ProjectExe.Integracao.entidades.CupomVenda;
import ProjectExe.Integracao.entidades.Venda;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CupomVendaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Cupom não pode ser nulo")
    private String vendaCupom;

    @NotNull(message = "Valor de desconto não pode ser nulo")
    private BigDecimal vlrDesconto;

    private Long venda;

    //Construtor com parâmetro da classe CupomVenda para CupomVendaDTO / BeanUtils necessita de setter além de getter no DTO
    public CupomVendaDTO(CupomVenda cupomVenda) {
        BeanUtils.copyProperties(cupomVenda, this);
        this.venda = cupomVenda.getVenda().getVendaId();
    }
}
